package com.zunix.craweler;

import java.io.File;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zunix.craweler.handler.EmailProcessDbHandler;
import com.zunix.craweler.handler.EmailProcessHandler;

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.url.WebURL;

/**
 * This class shows how you can crawl email on the web and store them in a folder. This is just for demonstration
 * purposes and doesn't scale for large number of email. For crawling millions of email you would need to store
 * downloaded email in a hierarchy of folders
 */
public class EmailCraweler extends WebCrawler
{
	private static final Logger logger = LoggerFactory.getLogger(EmailCraweler.class);

	private static final Pattern filters = Pattern.compile(".*(\\.(css|js|mid|mp2|mp3|mp4|wav|avi|mov|mpeg|ram|m4v|pdf|rm|smil|wmv|swf|wma|zip|rar|gz))$");

	// private static final Pattern EMAIL_PATTERNS =
	// Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");

	private static final Pattern EMAIL_PATTERNS = Pattern.compile("^.*(\\b[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,4}\\b).*$");
	
	public static Set<String> emails = new LinkedHashSet<String>();

	private static File storageFolder;
	private static String[] crawlDomains;
	
	private EmailProcessHandler emailProcessHandler = new EmailProcessDbHandler();

	public static void configure(String[] domain, String storageFolderName)
	{
		logger.debug("Crawl Domain is [{}]", Arrays.asList(domain));

		crawlDomains = domain;

		storageFolder = new File(storageFolderName);
		if (!storageFolder.exists())
		{
			storageFolder.mkdirs();
		}
	}

	@Override
	public boolean shouldVisit(Page referringPage, WebURL url)
	{
		String href = url.getURL().toLowerCase();
		if (filters.matcher(href).matches())
		{
			return false;
		}

		if (EMAIL_PATTERNS.matcher(href).matches())
		{
			return true;
		}

		for (String domain : crawlDomains)
		{
			if (href.startsWith(domain))
			{
				return true;
			}
		}
		return false;
	}

	@SuppressWarnings("unused")
	@Override
	public void visit(Page page)
	{
		String url = page.getWebURL().getURL();
		
		if (page.getParseData() instanceof HtmlParseData)
		{
			HtmlParseData htmlParseData = (HtmlParseData) page.getParseData();
			String text = htmlParseData.getText();
			String html = htmlParseData.getHtml();
			Set<WebURL> links = htmlParseData.getOutgoingUrls();
			
			Pattern p = Pattern.compile("\\b[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,4}\\b", Pattern.CASE_INSENSITIVE);
			Matcher matcher = p.matcher(html);

			while (matcher.find())
			{
//				emails.add(matcher.group());
			    String email = matcher.group();
			    emailProcessHandler.persist(email);
			}
		}

		if(emails.isEmpty())
		{
		    return ;
		}
		
//		String filename = storageFolder.getAbsolutePath() + "/" + "email.txt";
		
        try
        {
            //FileUtils.writeLines(new File(filename), emails);
            logger.info("Stored: {}", url);
        }
        catch (Exception iox)
        {
//            logger.error("Failed to write file: " + filename, iox);
        }
	}
}