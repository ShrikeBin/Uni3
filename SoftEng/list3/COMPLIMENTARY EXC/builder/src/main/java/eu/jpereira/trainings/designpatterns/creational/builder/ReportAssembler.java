package eu.jpereira.trainings.designpatterns.creational.builder;

import eu.jpereira.trainings.designpatterns.creational.builder.model.Report;
import eu.jpereira.trainings.designpatterns.creational.builder.model.SaleEntry;

import eu.jpereira.trainings.designpatterns.creational.builder.json.JSONReportBodyBuilder;
import eu.jpereira.trainings.designpatterns.creational.builder.xml.XMLReportBodyBuilder;
import eu.jpereira.trainings.designpatterns.creational.builder.html.HTMLReportBodyBuilder;


public class ReportAssembler {

    private SaleEntry saleEntry;

    /**
     * @param saleEntry
     */
    public void setSaleEntry(SaleEntry saleEntry) {
        this.saleEntry = saleEntry;
    }

    /**
     * @param type
     * @return
     */
    public Report getReport(String type) {
        ReportBodyBuilder builder;

        switch (type.toUpperCase()) {
            case "JSON":
                builder = new JSONReportBodyBuilder();
                break;
            case "XML":
                builder = new XMLReportBodyBuilder();
                break;
            case "HTML":
                builder = new HTMLReportBodyBuilder();
                break;
            default:
                throw new IllegalArgumentException("Unsupported report type: " + type);
        }

        builder.setSaleEntry(this.saleEntry);
        return builder.buildReport();
    }
}
