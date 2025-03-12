package eu.jpereira.trainings.designpatterns.creational.abstractfactory;

import eu.jpereira.trainings.designpatterns.creational.abstractfactory.xml.XMLReportBody;
import eu.jpereira.trainings.designpatterns.creational.abstractfactory.xml.XMLReportFooter;
import eu.jpereira.trainings.designpatterns.creational.abstractfactory.xml.XMLReportHeader;

// rwlodarczyk: Added class to use abstract factory pattern

public class XMLReportFactory implements ReportFactory {
    public ReportBody createBody() {
        return new XMLReportBody();
    }
    
    public ReportHeader createHeader() {
        return new XMLReportHeader();
    }
    
    public ReportFooter createFooter() {
        return new XMLReportFooter();
    }
}
