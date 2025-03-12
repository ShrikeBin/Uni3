package eu.jpereira.trainings.designpatterns.creational.abstractfactory;

import eu.jpereira.trainings.designpatterns.creational.abstractfactory.json.JSONReportBody;
import eu.jpereira.trainings.designpatterns.creational.abstractfactory.json.JSONReportFooter;
import eu.jpereira.trainings.designpatterns.creational.abstractfactory.json.JSONReportHeader;

// rwlodarczyk: Added class to use abstract factory pattern

public class JSONReportFactory implements ReportFactory {
    public ReportBody createBody() {
        return new JSONReportBody();
    }
    
    public ReportHeader createHeader() {
        return new JSONReportHeader();
    }
    
    public ReportFooter createFooter() {
        return new JSONReportFooter();
    }
}

