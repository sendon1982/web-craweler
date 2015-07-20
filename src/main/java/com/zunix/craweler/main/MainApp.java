package com.zunix.craweler.main;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zunix.craweler.EmailCraweler;

import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;

/**
 * Main App class to run the crawling, this is the entry point.
 * 
 * @author sendon1982
 *
 */
public class MainApp
{
	private static final Logger logger = LoggerFactory.getLogger(MainApp.class);
	
	public static void main(String[] args) throws Exception
	{
		logger.info("Start to execute Main method.");
		
		String crawlStorageFolder = "/usr/local/crawl/";
		int numberOfCrawlers = 10;

		CrawlConfig config = new CrawlConfig();
		config.setCrawlStorageFolder(crawlStorageFolder);

		/*
		 * Instantiate the controller for this crawl.
		 */
		PageFetcher pageFetcher = new PageFetcher(config);
		RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
		robotstxtConfig.setEnabled(false);
		RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);
		CrawlController controller = new CrawlController(config, pageFetcher, robotstxtServer);

		/*
		 * For each crawl, you need to add some seed urls. These are the first URLs that are fetched and then the
		 * crawler starts following links which are found in these pages
		 */
		controller.addSeed("http://bbs.tianya.cn/post-stocks-1342993-1.shtml");

		/**
		 * Domain is the control point where the crawler will stop.
		 */
		String[] domain = new String[]{
			"http://bbs.tianya.cn/",
		};
		
		EmailCraweler.configure(domain, crawlStorageFolder);

        /**
         * Start the crawl. This is a blocking operation, meaning that your code will reach the line after this only
         * when crawling is finished.
         */
		controller.start(EmailCraweler.class, numberOfCrawlers);
		
		System.out.println("************* Email list is below = " + EmailCraweler.emails.size() +  "*************");
	}
}
