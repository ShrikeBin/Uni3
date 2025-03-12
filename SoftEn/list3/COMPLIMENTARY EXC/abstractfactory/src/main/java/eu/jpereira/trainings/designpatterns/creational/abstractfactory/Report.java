/**
 * Copyright 2011 Joao Miguel Pereira
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package eu.jpereira.trainings.designpatterns.creational.abstractfactory;

import eu.jpereira.trainings.designpatterns.creational.abstractfactory.ReportFactory;
import eu.jpereira.trainings.designpatterns.creational.abstractfactory.JSONReportFactory;
import eu.jpereira.trainings.designpatterns.creational.abstractfactory.XMLReportFactory;


// Modified class to use abstract factory pattern

public class Report {

	private String reportContent;
	private ReportBody body;
	private ReportFooter footer;
	private ReportHeader header;
	private String reportType;
	

	
	
	/**
	 * @param string
	 */
	public Report(String reportType) {
        ReportFactory factory;

		switch (reportType) {
			case "JSON":
			factory = new JSONReportFactory();
			break;
			case "XML":
			factory = new XMLReportFactory();
			break;
			default:
			throw new IllegalArgumentException("Invalid report type");
		}

        this.body = factory.createBody();
        this.header = factory.createHeader();
        this.footer = factory.createFooter();
    }


	public void setBody(ReportBody body) {
		this.body = body;

	}

	
	public void setFooter(ReportFooter footer) {
		this.footer = footer;

	}

	
	public void setHeader(ReportHeader header) {
		this.header = header;
	}

	public void setReportContent(String reportContent) {
		this.reportContent = reportContent;
	}


	public String getReportContent() {
		return reportContent;
	}


	public ReportBody getBody() {
		return body;
	}


	public ReportFooter getFooter() {
		return footer;
	}


	public ReportHeader getHeader() {
		return header;
	}

	
}
