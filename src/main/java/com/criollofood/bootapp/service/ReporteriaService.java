package com.criollofood.bootapp.service;

import com.criollofood.bootapp.domain.ReporteAtencionItem;
import com.criollofood.bootapp.domain.ReporteCajaItem;
import com.criollofood.bootapp.domain.ReporteRentabilidadItem;
import com.criollofood.bootapp.sql.ReporteAtencionSP;
import com.criollofood.bootapp.sql.ReporteCajaSP;
import com.criollofood.bootapp.sql.ReporteRentabilidadProductosSP;
import com.criollofood.bootapp.sql.ReporteRentabilidadRecetasSP;
import com.criollofood.bootapp.utils.Utils;
import com.lowagie.text.Image;
import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import lombok.RequiredArgsConstructor;
import org.hibernate.cfg.NotYetImplementedException;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.NumberFormat;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.criollofood.bootapp.utils.TableUtils.*;

@Service
@RequiredArgsConstructor
public class ReporteriaService {

    private final ReporteCajaSP reporteCajaSP;
    private final ReporteAtencionSP reporteAtencionSP;
    private final ReporteRentabilidadRecetasSP reporteRentabilidadRecetasSP;
    private final ReporteRentabilidadProductosSP reporteRentabilidadProductosSP;

    public ByteArrayOutputStream obtenerReporte(String reporte, String fechaDesde, String fechaHasta) throws IOException {
        ByteArrayOutputStream bao;
        switch (reporte) {
            case "caja":
                bao = generarReporteCaja(fechaDesde, fechaHasta);
                break;
            case "atencion":
                bao = generarReporteAtencion(fechaDesde, fechaHasta);
                break;
            case "rentabilidad":
                bao = generarReporteRentabilidad(fechaDesde, fechaHasta);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + reporte);
        }
        return bao;
    }

    private List<ReporteCajaItem> obtenerReporteCaja(String fechaDesde, String fechaHasta) {
        return reporteCajaSP.execute(fechaDesde, fechaHasta);
    }

    private List<ReporteAtencionItem> obtenerReporteAtencion(String fechaDesde, String fechaHasta) {
        return reporteAtencionSP.execute(fechaDesde, fechaHasta);
    }

    private List<ReporteRentabilidadItem> obtenerReporteRentabilidadRecetas(String fechaDesde, String fechaHasta) {
        return reporteRentabilidadRecetasSP.execute(fechaDesde, fechaHasta);
    }

    private List<ReporteRentabilidadItem> obtenerReporteRentabilidadProductos(String fechaDesde, String fechaHasta) {
        return reporteRentabilidadProductosSP.execute(fechaDesde, fechaHasta);
    }

    public ByteArrayOutputStream generarReporteCaja(String fechaDesde, String fechaHasta) throws IOException {
        Document document = new Document();
        List<ReporteCajaItem> lista = obtenerReporteCaja(fechaDesde, fechaHasta);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfWriter.getInstance(document, baos);
        document.open();

        document.add(crearLogo());

        document.add(new Paragraph("Reporte de Caja", FONT_HEADING));
        document.add(new Paragraph(String.format("Desde el %s al %s", fechaDesde, fechaHasta), FONT_SUB_HEADING));

        Map<String, List<ReporteCajaItem>> dias = lista.stream()
                    .collect(Collectors.groupingBy(i -> Utils.formatDateToFull(i.getFecha())));

        dias.forEach((dia, items) -> {
            Paragraph title = new Paragraph(dia, FONT);
            title.setSpacingBefore(20);
            document.add(title);

            PdfPTable table = new PdfPTable(2);
            table.setHorizontalAlignment(Element.ALIGN_LEFT);
            table.setSpacingBefore(5);
            Stream.of("N° Caja","Total")
                .forEach(columnTitle -> {
                    PdfPCell header = new PdfPCell();
                    header.setBackgroundColor(Color.LIGHT_GRAY);
                    header.setBorderWidth(1);
                    header.setPadding(5);
                    header.setPhrase(new Phrase(columnTitle));
                    table.addCell(header);
                });
            for (ReporteCajaItem item : items) {
                table.addCell(item.getNumeroCaja().toString());
                table.addCell(NumberFormat.getCurrencyInstance().format(item.getTotal()));
            }
            document.add(table);
        });


        document.close();
        return baos;
    }

    public ByteArrayOutputStream generarReporteAtencion(String fechaDesde, String fechaHasta) throws IOException {
        Document document = new Document();
        List<ReporteAtencionItem> lista = obtenerReporteAtencion(fechaDesde, fechaHasta);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfWriter.getInstance(document, baos);
        document.open();

        document.add(crearLogo());

        document.add(new Paragraph("Reporte de Atención de clientes", FONT_HEADING));
        Paragraph subTitle = new Paragraph(String.format("Desde el %s al %s", fechaDesde, fechaHasta), FONT_SUB_HEADING);
        subTitle.setSpacingAfter(20);
        document.add(subTitle);

        lista.forEach(item -> {
            document.add(new Paragraph("Pedidos Realizados: " + item.getPedidosRealizados().toString(), FONT));
            document.add(new Paragraph("Tiempo de atención promedio (min): " + item.getPromedioTiempo().toString(), FONT));
            document.add(new Paragraph("Tiempo de preparación promedio (min): " + item.getPromedioTiempoPreparacion().toString(), FONT));
            document.add(new Paragraph("Tiempo de entrega promedio (min): " + item.getPromedioTiempoEntrega().toString(), FONT));
            document.add(new Paragraph("Receta más vendida: " + item.getRecetaMasVendida(), FONT));
        });

        document.close();
        return baos;
    }

    public ByteArrayOutputStream generarReporteRentabilidad(String fechaDesde, String fechaHasta) throws IOException{
        Document document = new Document();
        List<ReporteRentabilidadItem> listaRecetas = obtenerReporteRentabilidadRecetas(fechaDesde, fechaHasta);
        List<ReporteRentabilidadItem> listaProductos = obtenerReporteRentabilidadProductos(fechaDesde, fechaHasta);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfWriter.getInstance(document, baos);
        document.open();

        document.add(crearLogo());

        document.add(new Paragraph("Reporte de Rentabilidad", FONT_HEADING));
        Paragraph subTitle = new Paragraph(String.format("Desde el %s al %s", fechaDesde, fechaHasta), FONT_SUB_HEADING);
        document.add(subTitle);

        Paragraph titleRecetas = new Paragraph("Recetas", FONT);
        titleRecetas.setSpacingBefore(20);
        document.add(titleRecetas);
        document.add(crearTablaReporte(listaRecetas));

        Paragraph titleProductos = new Paragraph("Productos", FONT);
        titleRecetas.setSpacingBefore(20);
        document.add(titleProductos);
        document.add(crearTablaReporte(listaProductos));

        document.close();
        return baos;
    }

    private PdfPTable crearTablaReporte(List<ReporteRentabilidadItem> lista) {
        PdfPTable table = new PdfPTable(6);
        table.setHorizontalAlignment(Element.ALIGN_LEFT);
        table.setSpacingBefore(5);
        table.setSpacingAfter(20);
        table.setWidthPercentage(100);
        Stream.of("Nombre","Cantidad venta", "Total venta", "Precio costo", "Porcentaje ganancia", "Precio venta sugerido")
                .forEach(columnTitle -> {
                    PdfPCell header = new PdfPCell();
                    header.setBackgroundColor(Color.LIGHT_GRAY);
                    header.setBorderWidth(1);
                    header.setPadding(5);
                    header.setPhrase(new Phrase(columnTitle));
                    table.addCell(header);
                });
        for (ReporteRentabilidadItem item : lista) {
            table.addCell(item.getNombre());
            table.addCell(item.getCantidadVenta().toString());
            table.addCell(NumberFormat.getCurrencyInstance().format(item.getTotalVenta()));
            table.addCell(NumberFormat.getCurrencyInstance().format(item.getTotalPrecioCompra() != null ? item.getTotalPrecioCompra() : 0));
            table.addCell(item.getPorcentajeGanancia() != null ? item.getPorcentajeGanancia().toString()+"%" : "");
            table.addCell(NumberFormat.getCurrencyInstance().format(item.getPrecioVentaSugerido() != null ? item.getPrecioVentaSugerido() : 0));
        }

        return table;
    }

    private Image crearLogo() throws IOException {
        Image logo = Image.getInstance(Files.readAllBytes(Paths.get(LOGO_PATH)));
        logo.setAlignment(Element.ALIGN_RIGHT);
        logo.scaleToFit(90, 90);

        return logo;
    }
}
