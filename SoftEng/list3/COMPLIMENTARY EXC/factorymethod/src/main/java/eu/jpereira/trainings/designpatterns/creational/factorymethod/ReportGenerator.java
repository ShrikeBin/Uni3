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
package eu.jpereira.trainings.designpatterns.creational.factorymethod;

import java.util.Map;
import java.util.HashMap;

/**
 * The Report Generator will create reports based on a given type
 * 
 * @author jpereira
 *
 */
public class ReportGenerator {
	private final Map<String, ReportFactory> factoryMap;

	public ReportGenerator() {
		factoryMap = new HashMap<>();
		factoryMap.put("JSON", new JSONReportFactory());
		factoryMap.put("XML", new XMLReportFactory());
		factoryMap.put("HTML", new HTMLReportFactory());
		factoryMap.put("PDF", new PDFReportFactory());
	}

	public Report generateReport(ReportData data, String type) {
		ReportFactory factory = factoryMap.get(type);
		if (factory == null) {
			return null;
		}

		Report report = factory.createReport();
		report.generateReport(data);
		return report;
	}
}
