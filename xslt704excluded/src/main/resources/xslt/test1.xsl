<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:java="java"
	xmlns:fo="http://www.w3.org/1999/XSL/Format"
	exclude-result-prefixes="fo java">
	<xsl:variable name="resources"
		select="java:util.ResourceBundle.getBundle('uk.me.kissy.messages')" />
	<xsl:template match="someXml">
		<fo:root xmlns:fo="http://www.w3.org/1999/XSL/Format">
			<fo:page-sequence>
				<fo:block>
					<xsl:text>Resources:</xsl:text>
					<xsl:value-of
						select="java:getString($resources,'property1')" />
				</fo:block>
			</fo:page-sequence>
		</fo:root>
	</xsl:template>
</xsl:stylesheet>