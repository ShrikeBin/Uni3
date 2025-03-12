package eu.jpereira.trainings.designpatterns.creational.abstractfactory;

// Added interface for the abstract factory pattern

public interface ReportFactory {
    ReportBody createBody();
    ReportHeader createHeader();
    ReportFooter createFooter();
}
