package com.criollofood.bootapp.utils;

import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import org.springframework.stereotype.Component;
import java.awt.*;

public class TableUtils {
    public final static Font FONT = FontFactory.getFont(FontFactory.HELVETICA, 16, Color.BLACK);
    public final static Font FONT_HEADING = FontFactory.getFont(FontFactory.HELVETICA, 24, Color.BLACK);
    public final static Font FONT_SUB_HEADING = FontFactory.getFont(FontFactory.HELVETICA, 14, Color.GRAY);
    public final static String LOGO_PATH = "src/main/resources/static/images/criollo-food-logo.png";
}
