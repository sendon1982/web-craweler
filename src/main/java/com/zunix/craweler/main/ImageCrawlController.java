package com.zunix.craweler.main;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zunix.craweler.ImageCraweler;

import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;

/**
 * @author sendon1982
 */
public class ImageCrawlController
{
	private static final Logger logger = LoggerFactory.getLogger(ImageCrawlController.class);

	public static void main(String[] args) throws Exception
	{
		String rootFolder = "/usr/local/crawl/";
		int numberOfCrawlers = 1;
		String storageFolder = "/usr/local/crawl/";

		CrawlConfig config = new CrawlConfig();

		config.setCrawlStorageFolder(rootFolder);

		/*
		 * Since images are binary content, we need to set this parameter to true to make sure they are included in the
		 * crawl.
		 */
		config.setIncludeBinaryContentInCrawling(true);

		String[] crawlDomains =
		{ "http://uci.edu/" };

		PageFetcher pageFetcher = new PageFetcher(config);
		RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
		RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);
		CrawlController controller = new CrawlController(config, pageFetcher, robotstxtServer);
		for (String domain : crawlDomains)
		{
			controller.addSeed(domain);
		}

		ImageCraweler.configure(crawlDomains, storageFolder);

		controller.start(ImageCraweler.class, numberOfCrawlers);
	}
}