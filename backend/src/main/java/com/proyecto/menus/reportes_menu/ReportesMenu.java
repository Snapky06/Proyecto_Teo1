package com.proyecto.menus.reportes_menu;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.proyecto.config.Database;
import com.proyecto.cruds.CategoriaDAO;
import com.proyecto.cruds.SubcategoriaDAO;
import com.proyecto.cruds.PresupuestoDAO;
import com.proyecto.menus.MenuBase;
import com.proyecto.menus.MenuHelper;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.Map;
import java.util.Scanner;

public class ReportesMenu extends MenuBase {
    private final Font fontTitulo = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16, BaseColor.BLACK);
    private final Font fontSubtitulo = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12, BaseColor.BLACK);
    private final Font fontNormal = FontFactory.getFont(FontFactory.HELVETICA, 11, BaseColor.BLACK);
    private final Font fontVerde = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 11, new BaseColor(34, 139, 34));
    private final Font fontNaranja = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 11, BaseColor.ORANGE);
    private final Font fontRojo = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 11, BaseColor.RED);
    private final SubcategoriaDAO subcategoriaDAO = new SubcategoriaDAO();
    private final CategoriaDAO categoriaDAO = new CategoriaDAO();
    private final PresupuestoDAO presupuestoDAO = new PresupuestoDAO();

    public ReportesMenu(Scanner scanner) {
        super(scanner);
    }

    @Override
    protected void mostrarMenu() {
        System.out.println("\n=== MODULO DE REPORTERIA Y EXPORTACION PDF ===");
        System.out.println("1. Exportar Reporte 1: Resumen Mensual de Ingresos vs Gastos");
        System.out.println("2. Exportar Reporte 2: Distribucion de Gastos por Categoria");
        System.out.println("3. Exportar Reporte 3: Analisis de Cumplimiento de Presupuesto");
        System.out.println("4. Exportar Reporte 4: Estado de Obligaciones Fijas");
        System.out.println("5. Exportar Reporte 5: Proyeccion de Gastos a Fin de Mes");
        System.out.println("6. Exportar Reporte 6: Analisis Historico y Promedio de Gastos");
        System.out.println("0. Volver al menu principal");
    }

    @Override
    protected boolean ejecutarOpcion(int opcion) {
        switch (opcion) {
            case 1:
                generarReporteBalanceMensual();
                break;
            case 2:
                generarReporteDistribucionGastos();
                break;
            case 3:
                generarReporteCumplimiento();
                break;
            case 4:
                generarReporteObligaciones();
                break;
            case 5:
                generarReporteProyeccion();
                break;
            case 6:
                generarReportePromedio();
                break;
            case 0:
                return true;
            default:
                System.out.println("Opcion no valida.");
        }
        return false;
    }

    private String generarBarraAscii(double porcentaje) {
        int fill = (int) (porcentaje / 10);
        fill = Math.min(10, Math.max(0, fill));
        StringBuilder b = new StringBuilder("[");
        for (int i = 0; i < 10; i++) {
            b.append(i < fill ? "=" : " ");
        }
        b.append("]");
        return b.toString();
    }

    private void generarReporteBalanceMensual() {
        try {
            int idUsuario = obtenerIdUsuario();
            System.out.println("\n=================================================");
            System.out.println("          LISTA DE PRESUPUESTOS DISPONIBLES      ");
            System.out.println("=================================================");
            presupuestoDAO.listarPresupuestos(idUsuario);
            System.out.println("=================================================");
            int idPresupuesto = MenuHelper.leerEntero(scanner, "ID del presupuesto");
            short anio = MenuHelper.leerShort(scanner, "Anio (Ej. 2026)");
            short mes = MenuHelper.leerMes(scanner, "Mes (1-12)");
            String archivo = "Reporte_1_Balance_U" + idUsuario + "_" + anio + "_" + mes + ".pdf";
            
            try (Connection conn = Database.obtenerConexion();
                 CallableStatement cs = conn.prepareCall("{ call sp_calcular_balance_mensual(?, ?, ?, ?) }")) {
                cs.setInt(1, idUsuario);
                cs.setInt(2, idPresupuesto);
                cs.setShort(3, anio);
                cs.setShort(4, mes);
                try (ResultSet rs = cs.executeQuery()) {
                    if (rs.next()) {
                        Document document = new Document();
                        PdfWriter.getInstance(document, new FileOutputStream(archivo));
                        document.open();
                        document.add(new Paragraph("Reporte 1: Resumen Mensual de Ingresos vs Gastos", fontTitulo));
                        document.add(new Paragraph("Periodo: " + mes + "/" + anio + "\n\n", fontNormal));
                        BigDecimal ingresos = rs.getBigDecimal("total_ingresos");
                        BigDecimal gastos = rs.getBigDecimal("total_gastos");
                        BigDecimal ahorros = rs.getBigDecimal("total_ahorros");
                        BigDecimal balance = rs.getBigDecimal("balance_final");
                        double maximo = Math.max(ingresos.doubleValue(), gastos.doubleValue());
                        if (maximo == 0) maximo = 1;
                        document.add(new Paragraph("Total Ingresos: L. " + ingresos, fontVerde));
                        document.add(new Paragraph(generarBarraAscii((ingresos.doubleValue() / maximo) * 100) + "\n\n", fontNormal));
                        document.add(new Paragraph("Total Gastos: L. " + gastos, fontRojo));
                        document.add(new Paragraph(generarBarraAscii((gastos.doubleValue() / maximo) * 100) + "\n\n", fontNormal));
                        document.add(new Paragraph("Total Ahorros: L. " + ahorros, fontNaranja));
                        document.add(new Paragraph("\nBalance Final Disponible: L. " + balance, fontSubtitulo));
                        document.close();
                        System.out.println("Exito: Se ha generado el archivo " + archivo);
                    } else {
                        System.out.println("No hay datos para generar el reporte.");
                    }
                }
            } catch (Exception e) {
                System.out.println("Error al generar PDF: " + e.getMessage());
            }
        } catch (MenuHelper.OperacionCanceladaException e) {
            System.out.println("\n[!] Operacion cancelada por el usuario.\n");
        }
    }

    private void generarReporteDistribucionGastos() {
        try {
            int idUsuario = obtenerIdUsuario();
            short anio = MenuHelper.leerShort(scanner, "Anio (Ej. 2026): ");
            short mes = MenuHelper.leerMes(scanner, "Mes (1-12): ");
            String archivo = "Reporte_2__Distribucion_U" + idUsuario + "_" + anio + "_" + mes + ".pdf";
            
            try {
                Document document = new Document();
                PdfWriter.getInstance(document, new FileOutputStream(archivo));
                document.open();
                document.add(new Paragraph("Reporte 2: Distribucion de Gastos por Categoria", fontTitulo));
                document.add(new Paragraph("Periodo: " + mes + "/" + anio + "\n\n", fontNormal));
                
                Map<Integer, String> categorias = categoriaDAO.obtenerCategoriasPorTipo(idUsuario, "GASTO");
                Map<String, BigDecimal> gastosPorCategoria = new java.util.LinkedHashMap<>();
                BigDecimal granTotal = BigDecimal.ZERO;
                
                String sqlTotal = "{ call fn_obtener_total_ejecutado_categoria_mes(?, ?, ?) }";
                try (Connection conn = Database.obtenerConexion();
                     CallableStatement csTotal = conn.prepareCall(sqlTotal)) {
                    for (Map.Entry<Integer, String> entry : categorias.entrySet()) {
                        csTotal.setInt(1, entry.getKey());
                        csTotal.setShort(2, anio);
                        csTotal.setShort(3, mes);
                        try (ResultSet rsTotal = csTotal.executeQuery()) {
                            if (rsTotal.next() && rsTotal.getBigDecimal(1) != null) {
                                BigDecimal totalCat = rsTotal.getBigDecimal(1);
                                if (totalCat.compareTo(BigDecimal.ZERO) > 0) {
                                    gastosPorCategoria.put(entry.getValue(), totalCat);
                                    granTotal = granTotal.add(totalCat);
                                }
                            }
                        }
                    }
                }
                
                document.add(new Paragraph("Gasto Total del Mes: L. " + granTotal + "\n\n", fontSubtitulo));
                if (granTotal.compareTo(BigDecimal.ZERO) > 0) {
                    for (Map.Entry<String, BigDecimal> entry : gastosPorCategoria.entrySet()) {
                        String nombreCat = entry.getKey();
                        BigDecimal totalCat = entry.getValue();
                        double pct = (totalCat.doubleValue() / granTotal.doubleValue()) * 100;
                        String linea = String.format("%s: L. %.2f (%.1f%%)", nombreCat, totalCat, pct);
                        document.add(new Paragraph(linea, fontNormal));
                        document.add(new Paragraph(generarBarraAscii(pct) + "\n", fontNormal));
                    }
                }
                
                document.close();
                System.out.println("Exito: Se ha generado el archivo " + archivo);
            } catch (Exception e) {
                System.out.println("Error al generar PDF: " + e.getMessage());
            }
        } catch (MenuHelper.OperacionCanceladaException e) {
            System.out.println("\n[!] Operacion cancelada por el usuario.\n");
        }
    }

    private void generarReporteCumplimiento() {
        try {
            int idUsuario = obtenerIdUsuario();
            System.out.println("\n=================================================");
            System.out.println("          LISTA DE PRESUPUESTOS DISPONIBLES      ");
            System.out.println("=================================================");
            presupuestoDAO.listarPresupuestos(idUsuario);
            System.out.println("=================================================");
            int idPresupuesto = MenuHelper.leerEntero(scanner, "ID del presupuesto");
            short anio = MenuHelper.leerShort(scanner, "Anio (Ej. 2026)");
            short mes = MenuHelper.leerMes(scanner, "Mes (1-12)");
            String archivo = "Reporte_3_Cumplimiento_Presupuesto_U" + idUsuario + "_" + anio + "_" + mes + ".pdf";
            
            try {
                Document document = new Document();
                PdfWriter.getInstance(document, new FileOutputStream(archivo));
                document.open();
                document.add(new Paragraph("Reporte 3: Analisis de Cumplimiento de Presupuesto", fontTitulo));
                document.add(new Paragraph("Periodo: " + mes + "/" + anio + "\n\n", fontNormal));
                Map<Integer, String> categorias = categoriaDAO.obtenerCategoriasPorTipo(idUsuario, "GASTO");
                String sqlResumen = "{ call sp_obtener_resumen_categoria_mes(?, ?, ?, ?) }";
                String sqlPctSub = "{ call fn_calcular_porcentaje_ejecutado(?, ?, ?, ?) }";
                String sqlMontoSub = "{ call fn_calcular_monto_ejecutado(?, ?, ?) }";
                try (Connection conn = Database.obtenerConexion();
                     CallableStatement csRes = conn.prepareCall(sqlResumen);
                     CallableStatement csPct = conn.prepareCall(sqlPctSub);
                     CallableStatement csMon = conn.prepareCall(sqlMontoSub)) {
                    for (Map.Entry<Integer, String> entryCat : categorias.entrySet()) {
                        int idCat = entryCat.getKey();
                        String nombreCat = entryCat.getValue();
                        csRes.setInt(1, idCat);
                        csRes.setInt(2, idPresupuesto);
                        csRes.setShort(3, anio);
                        csRes.setShort(4, mes);
                        try (ResultSet rsRes = csRes.executeQuery()) {
                            if (rsRes.next()) {
                                BigDecimal ppto = rsRes.getBigDecimal("monto_presupuestado");
                                BigDecimal ejec = rsRes.getBigDecimal("monto_ejecutado");
                                document.add(new Paragraph("Categoria: " + nombreCat + " (Presupuestado: L." + ppto + " / Ejecutado: L." + ejec + ")", fontSubtitulo));
                                Map<Integer, String> subcategorias = subcategoriaDAO.obtenerSubcategoriasPorCategoria(idCat);
                                for (Map.Entry<Integer, String> entrySub : subcategorias.entrySet()) {
                                    int idSub = entrySub.getKey();
                                    String nomSub = entrySub.getValue();
                                    BigDecimal subPct = BigDecimal.ZERO;
                                    BigDecimal subMonto = BigDecimal.ZERO;
                                    csPct.setInt(1, idSub);
                                    csPct.setInt(2, idPresupuesto);
                                    csPct.setShort(3, anio);
                                    csPct.setShort(4, mes);
                                    try (ResultSet rsPct = csPct.executeQuery()) {
                                        if (rsPct.next() && rsPct.getBigDecimal(1) != null) {
                                            subPct = rsPct.getBigDecimal(1);
                                        }
                                    }
                                    csMon.setInt(1, idSub);
                                    csMon.setShort(2, anio);
                                    csMon.setShort(3, mes);
                                    try (ResultSet rsMon = csMon.executeQuery()) {
                                        if (rsMon.next() && rsMon.getBigDecimal(1) != null) {
                                            subMonto = rsMon.getBigDecimal(1);
                                        }
                                    }
                                    Font fuenteColor = fontVerde;
                                    if (subPct.doubleValue() > 100) fuenteColor = fontRojo;
                                    else if (subPct.doubleValue() >= 80) fuenteColor = fontNaranja;
                                    document.add(new Paragraph("   - " + nomSub + ": Ejecutado L." + subMonto + " (" + subPct + "%)", fuenteColor));
                                }
                                document.add(new Paragraph("\n"));
                            }
                        }
                    }
                }
                document.close();
                System.out.println("Exito: Se ha generado el archivo " + archivo);
            } catch (Exception e) {
                System.out.println("Error al generar PDF: " + e.getMessage());
            }
        } catch (MenuHelper.OperacionCanceladaException e) {
            System.out.println("\n[!] Operacion cancelada por el usuario.\n");
        }
    }

    private void generarReporteObligaciones() {
        try {
            int idUsuario = obtenerIdUsuario();
            System.out.println("\n=================================================");
            System.out.println("          LISTA DE PRESUPUESTOS DISPONIBLES      ");
            System.out.println("=================================================");
            presupuestoDAO.listarPresupuestos(idUsuario);
            System.out.println("=================================================");
            int idPresupuesto = MenuHelper.leerEntero(scanner, "ID del presupuesto");
            short anio = MenuHelper.leerShort(scanner, "Anio (Ej. 2026)");
            short mes = MenuHelper.leerMes(scanner, "Mes (1-12)");
            String archivo = "Reporte_4_Estado_Obligaciones_U" + idUsuario + "_" + anio + "_" + mes + ".pdf";
            
            try (Connection conn = Database.obtenerConexion();
                 CallableStatement cs = conn.prepareCall("{ call sp_procesar_obligaciones_mes(?, ?, ?, ?) }")) {
                cs.setInt(1, idUsuario);
                cs.setShort(2, anio);
                cs.setShort(3, mes);
                cs.setInt(4, idPresupuesto);
                Document document = new Document();
                PdfWriter.getInstance(document, new FileOutputStream(archivo));
                document.open();
                document.add(new Paragraph("Reporte 4: Estado de Obligaciones Fijas", fontTitulo));
                document.add(new Paragraph("Periodo: " + mes + "/" + anio + "\n\n", fontNormal));
                PdfPTable tabla = new PdfPTable(5);
                tabla.setWidthPercentage(100);
                tabla.addCell(new PdfPCell(new Phrase("Obligacion", fontSubtitulo)));
                tabla.addCell(new PdfPCell(new Phrase("Monto", fontSubtitulo)));
                tabla.addCell(new PdfPCell(new Phrase("Vencimiento", fontSubtitulo)));
                tabla.addCell(new PdfPCell(new Phrase("Dias Restantes", fontSubtitulo)));
                tabla.addCell(new PdfPCell(new Phrase("Estado", fontSubtitulo)));
                try (ResultSet rs = cs.executeQuery()) {
                    while (rs.next()) {
                        String estado = rs.getString("p_estado_pago");
                        Font fuenteColor = fontNormal;
                        if (estado.equals("PAGADA")) fuenteColor = fontVerde;
                        else if (estado.equals("VENCIDA")) fuenteColor = fontRojo;
                        else if (estado.equals("POR VENCER")) fuenteColor = fontNaranja;
                        tabla.addCell(new Phrase(rs.getString("p_nombre"), fontNormal));
                        tabla.addCell(new Phrase(rs.getBigDecimal("p_monto_fijo_mensual").toString(), fontNormal));
                        tabla.addCell(new Phrase(rs.getDate("p_fecha_vencimiento").toString(), fontNormal));
                        tabla.addCell(new Phrase(String.valueOf(rs.getInt("p_dias_hasta_vencimiento")), fontNormal));
                        tabla.addCell(new Phrase(estado, fuenteColor));
                    }
                }
                document.add(tabla);
                document.close();
                System.out.println("Exito: Se ha generado el archivo " + archivo);
            } catch (Exception e) {
                System.out.println("Error al generar PDF: " + e.getMessage());
            }
        } catch (MenuHelper.OperacionCanceladaException e) {
            System.out.println("\n[!] Operacion cancelada por el usuario.\n");
        }
    }

    private void generarReporteProyeccion() {
        try {
            int idUsuario = obtenerIdUsuario();
            System.out.println("\n=================================================");
            System.out.println("          DIRECTORIO DE SUBCATEGORIAS            ");
            System.out.println("=================================================");
            Map<Integer, String> categorias = categoriaDAO.obtenerCategoriasPorTipo(idUsuario, "INGRESO");
            categorias.putAll(categoriaDAO.obtenerCategoriasPorTipo(idUsuario, "GASTO"));
            for (Map.Entry<Integer, String> entry : categorias.entrySet()) {
                System.out.println("\n-> Categoria ID: " + entry.getKey() + " | Nombre: " + entry.getValue());
                subcategoriaDAO.listarSubcategorias(entry.getKey());
            }
            System.out.println("=================================================");
            int idSubcategoria = MenuHelper.leerEntero(scanner, "ID de la subcategoria");
            short anio = MenuHelper.leerShort(scanner, "Anio (Ej. 2026)");
            short mes = MenuHelper.leerMes(scanner, "Mes (1-12)");
            String archivo = "Reporte_5_Proyeccion_Gasto_U" + idSubcategoria + "_" + anio + "_" + mes + ".pdf";
            
            try (Connection conn = Database.obtenerConexion()) {
                Document document = new Document();
                PdfWriter.getInstance(document, new FileOutputStream(archivo));
                document.open();
                document.add(new Paragraph("Reporte 5: Proyeccion de Gasto a Fin de Mes", fontTitulo));
                document.add(new Paragraph("Subcategoria ID: " + idSubcategoria + " | Periodo: " + mes + "/" + anio + "\n\n", fontNormal));
                String sqlPadre = "{ call fn_obtener_categoria_por_subcategoria(?) }";
                try (CallableStatement csP = conn.prepareCall(sqlPadre)) {
                    csP.setInt(1, idSubcategoria);
                    try (ResultSet rsP = csP.executeQuery()) {
                        if (rsP.next()) {
                            document.add(new Paragraph("ID Categoria Padre Mapeada: " + rsP.getInt(1), fontNormal));
                        }
                    }
                }
                String sqlProy = "{ call fn_calcular_proyeccion_gasto_mensual(?, ?, ?) }";
                try (CallableStatement cs = conn.prepareCall(sqlProy)) {
                    cs.setInt(1, idSubcategoria);
                    cs.setShort(2, anio);
                    cs.setShort(3, mes);
                    try (ResultSet rs = cs.executeQuery()) {
                        if (rs.next()) {
                            document.add(new Paragraph("\nSegun su ritmo de consumo diario actual, al finalizar el mes el gasto estimado sera de:", fontNormal));
                            document.add(new Paragraph("L. " + rs.getBigDecimal(1), fontRojo));
                        }
                    }
                }
                document.close();
                System.out.println("Exito: Se ha generado el archivo " + archivo);
            } catch (Exception e) {
                System.out.println("Error al generar PDF: " + e.getMessage());
            }
        } catch (MenuHelper.OperacionCanceladaException e) {
            System.out.println("\n[!] Operacion cancelada por el usuario.\n");
        }
    }

    private void generarReportePromedio() {
        try {
            int idUsuario = obtenerIdUsuario();
            System.out.println("\n=================================================");
            System.out.println("          DIRECTORIO DE SUBCATEGORIAS            ");
            System.out.println("=================================================");
            Map<Integer, String> categorias = categoriaDAO.obtenerCategoriasPorTipo(idUsuario, "INGRESO");
            categorias.putAll(categoriaDAO.obtenerCategoriasPorTipo(idUsuario, "GASTO"));
            for (Map.Entry<Integer, String> entry : categorias.entrySet()) {
                System.out.println("\n-> Categoria ID: " + entry.getKey() + " | Nombre: " + entry.getValue());
                subcategoriaDAO.listarSubcategorias(entry.getKey());
            }
            System.out.println("=================================================");
            int idSubcategoria = MenuHelper.leerEntero(scanner, "ID de la subcategoria");
            int meses = MenuHelper.leerEntero(scanner, "Cantidad de meses a analizar");
            String archivo = "Reporte_6_Promedio_Historico_U" + idUsuario + "_" + meses + ".pdf";
            
            try (Connection conn = Database.obtenerConexion();
                 CallableStatement cs = conn.prepareCall("{ call fn_obtener_promedio_gasto_subcategoria(?, ?, ?) }")) {
                cs.setInt(1, idUsuario);
                cs.setInt(2, idSubcategoria);
                cs.setInt(3, meses);
                Document document = new Document();
                PdfWriter.getInstance(document, new FileOutputStream(archivo));
                document.open();
                document.add(new Paragraph("Reporte 6: Analisis Historico y Promedio", fontTitulo));
                document.add(new Paragraph("Analisis de los ultimos " + meses + " meses.\n\n", fontNormal));
                try (ResultSet rs = cs.executeQuery()) {
                    if (rs.next()) {
                        document.add(new Paragraph("El promedio de gasto en esta subcategoria a lo largo del tiempo indicado es de:", fontNormal));
                        document.add(new Paragraph("L. " + rs.getBigDecimal(1), fontSubtitulo));
                    }
                }
                document.close();
                System.out.println("Exito: Se ha generado el archivo " + archivo);
            } catch (Exception e) {
                System.out.println("Error al generar PDF: " + e.getMessage());
            }
        } catch (MenuHelper.OperacionCanceladaException e) {
            System.out.println("\n[!] Operacion cancelada por el usuario.\n");
        }
    }
}