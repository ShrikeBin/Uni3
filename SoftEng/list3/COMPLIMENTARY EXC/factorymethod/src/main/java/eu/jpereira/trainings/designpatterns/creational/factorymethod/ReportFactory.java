package eu.jpereira.trainings.designpatterns.creational.factorymethod;

import java.util.HashMap;
import java.util.Map;

// rwlodarczyk: Create ReportFactory interface and its implementations

interface ReportFactory {
    Report createReport();
}

class JSONReportFactory implements ReportFactory {
    @Override
    public Report createReport() {
        return new JSONReport();
    }
}

class XMLReportFactory implements ReportFactory {
    @Override
    public Report createReport() {
        return new XMLReport();
    }
}

class HTMLReportFactory implements ReportFactory {
    @Override
    public Report createReport() {
        return new HTMLReport();
    }
}

class PDFReportFactory implements ReportFactory {
    @Override
    public Report createReport() {
        return new PDFReport();
    }
}