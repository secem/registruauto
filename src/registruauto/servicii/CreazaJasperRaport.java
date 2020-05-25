package registruauto.servicii;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.export.ExporterInput;
import net.sf.jasperreports.export.OutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimplePdfExporterConfiguration;
import registruauto.db.MyDataSource;
import registruauto.gui.util.AlertsUtil;

//import org.o7planning.tutorial.javajasperreport.conn.ConnectionUtils;
public class CreazaJasperRaport {

    private static final Logger LOG = Logger.getLogger(CreazaJasperRaport.class.getName());

    public static void create(int id) throws JRException, ClassNotFoundException, SQLException, Exception {

        //Report file location
        String reportSrcFile = new File("").getAbsolutePath() + "/src/registruauto/servicii/Simple_Blue_5.jrxml";

        // First, compile jrxml file.
        JasperReport jasperReport = JasperCompileManager.compileReport(reportSrcFile);

        //Establish connection to the DB
        MyDataSource ds = MyDataSource.getInstance();
        Connection conn = ds.getConnection();

        // Parameters for report
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("a.id", id);
        JasperPrint print = JasperFillManager.fillReport(jasperReport, parameters, conn);

        // Make sure the output directory exists.
        File outDir = new File("./Reports");
        outDir.mkdirs();

        // PDF Exportor.
        JRPdfExporter exporter = new JRPdfExporter();
        ExporterInput exporterInput = new SimpleExporterInput(print);

        // ExporterInput
        exporter.setExporterInput(exporterInput);

        // ExporterOutput
        OutputStreamExporterOutput exporterOutput = new SimpleOutputStreamExporterOutput("./Reports/RaportAuto.pdf");
        // Output
        exporter.setExporterOutput(exporterOutput);

        //
        SimplePdfExporterConfiguration configuration = new SimplePdfExporterConfiguration();
        exporter.setConfiguration(configuration);
        exporter.exportReport();

        LOG.info("Raportul este generat cu succes.");
    }

    public static void openReport() {
        AlertsUtil alert = new AlertsUtil();

        if (Desktop.isDesktopSupported()) {
            try {
                File myFile = new File("./Reports/RaportAuto.pdf");
                Desktop.getDesktop().open(myFile);
            } catch (IOException ex) {
                // no application registered for PDFs
            }
        }
    }
}
