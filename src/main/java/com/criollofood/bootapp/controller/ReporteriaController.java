package com.criollofood.bootapp.controller;

import com.criollofood.bootapp.service.ReporteriaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

@Controller
@RequiredArgsConstructor
public class ReporteriaController {

    private final ReporteriaService  reporteriaService;

    @GetMapping("/reporteria")
    public String reporteria() {
        return "redirect:/reporteria/caja";
    }

    @GetMapping("/reporteria/{reporte}")
    public ModelAndView reporteria(@PathVariable String reporte) {
        ModelAndView modelAndView = new ModelAndView("reportes");
        modelAndView.addObject("reporte", reporte);
        return modelAndView;
    }

    @GetMapping("/reporteria/{reporte}/descargar")
    public void descargarReporte(@PathVariable String reporte, @RequestParam String fechaDesde, @RequestParam String fechaHasta, HttpServletResponse response) throws IOException {
        ByteArrayOutputStream baos = reporteriaService.obtenerReporte(reporte, fechaDesde, fechaHasta);
        String filename = String.format("reporte_%s.pdf", reporte);

        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "inline; filename=\"" + filename + "\"");
        response.setContentLength(baos.size());
        OutputStream os = response.getOutputStream();
        baos.writeTo(os);
        os.flush();
        os.close();
    }
}
