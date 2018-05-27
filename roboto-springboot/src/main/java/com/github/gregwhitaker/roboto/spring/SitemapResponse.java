/*
 * Copyright 2018 Greg Whitaker
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.gregwhitaker.roboto.spring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Builds a sitemap.xml response based on the supplied {@link RobotoMapper}.
 */
public class SitemapResponse {
    private static final Logger LOGGER = LoggerFactory.getLogger(SitemapResponse.class);
    private static final Map<RobotoMapper, XmlUrlSet> CACHE = new HashMap<>();

    /**
     *
     * @param request
     * @param mapper
     * @return
     */
    public static XmlUrlSet create(HttpServletRequest request, RobotoMapper mapper) {
        return CACHE.computeIfAbsent(mapper, sitemap -> {
            Set<String> allowed = mapper.getAllowed();

            XmlUrlSet xmlUrlSet = new XmlUrlSet();

            try {
                URI uri = new URI(request.getRequestURL().toString());

                for (String path : allowed) {
                    StringBuilder builder = new StringBuilder();
                    builder.append(uri.getScheme())
                            .append("://")
                            .append(uri.getHost())
                            .append(":")
                            .append(uri.getPort())
                            .append(path);

                    xmlUrlSet.addUrl(new XmlUrl(builder.toString()));
                }

                return xmlUrlSet;
            } catch (URISyntaxException e) {
                LOGGER.error("Failed to create sitemap.xml", e);
                throw new RuntimeException(e);
            }
        });
    }

    @XmlAccessorType(value = XmlAccessType.NONE)
    @XmlRootElement(name = "urlset", namespace = "http://www.sitemaps.org/schemas/sitemap/0.9")
    public static class XmlUrlSet {

        @XmlElements({@XmlElement(name = "url", type = XmlUrl.class)})
        private Collection<XmlUrl> xmlUrls = new ArrayList<>();

        public void addUrl(XmlUrl xmlUrl) {
            xmlUrls.add(xmlUrl);
        }

        public Collection<XmlUrl> getXmlUrls() {
            return xmlUrls;
        }
    }

    @XmlAccessorType(value = XmlAccessType.NONE)
    @XmlRootElement(name = "url")
    public static class XmlUrl {

        private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        @XmlElement
        private String loc;

        @XmlElement
        private String lastmod = sdf.format(new Date());

        @XmlElement
        private String changefreq = "monthly";

        public XmlUrl() {

        }

        public XmlUrl(String loc) {
            this.loc = loc;
        }

        public String getLoc() {
            return loc;
        }

        public String getChangefreq() {
            return changefreq;
        }

        public String getLastmod() {
            return lastmod;
        }
    }
}
