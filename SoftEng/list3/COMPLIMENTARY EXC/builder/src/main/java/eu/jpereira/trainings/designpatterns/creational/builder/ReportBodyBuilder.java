package eu.jpereira.trainings.designpatterns.creational.builder;

import eu.jpereira.trainings.designpatterns.creational.builder.model.Report;
import eu.jpereira.trainings.designpatterns.creational.builder.model.SaleEntry;

// create reportbodybuilder interface

public interface ReportBodyBuilder {
    void setSaleEntry(SaleEntry saleEntry);
    Report buildReport();
}
