<?xml version="1.0" encoding="UTF-8" ?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="html" indent="yes" encoding="UTF-8"
		omit-xml-declaration="yes" />
	<xsl:template match="root">

		<html>
			<head>
				<title>
					<xsl:value-of select="site/title" />
				</title>
				<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
			</head>
			<body style="padding:0; margin:0;">
				<div class="container">
					<table cellpadding="0" cellspacing="0" border="0"
						style="font-family:'Tahoma', Arial, sans-serif; font-size:14px; line-height:1.5em; color:#414141; width:637px; padding:0px 0px 0px 0px; background-color:#e1fee2;"
						align="center">
						<!-- TOPO -->
						<tr>
							<td style="padding:0px 0px 0px 0px; margin:0px 0px 0px 0px;">
								<!-- a href="{site/component/content/url}" target="_blank"><img src="{site/url}img/content/header_v2.png?limpa=cache_eg" 
									alt="UNIMED Curitiba" title="UNIMED Curitiba" style="border-style:none;" 
									/></a -->
								<img src="{url}/resources/img/header_v2.png?limpa=cache_eg"
									alt="UNIMED Curitiba" title="UNIMED Curitiba" style="border-style:none;" />
							</td>
						</tr>

						<tr>
							<td
								style="padding:24px 24px 20px 24px; margin:0px 0px 0px 0px; border-bottom:solid 0px #f5f5f5;">
								Prezado(a) Cooperado(a),
								<strong>
									<xsl:value-of select="prestador/nome"
										disable-output-escaping="yes" />
								</strong>
							</td>
						</tr>

						<tr>
							<td
								style="padding:20px 24px 20px 24px; margin:0px 0px 0px 0px; border-bottom:solid 0px #f5f5f5;">
								<p>
									<xsl:value-of select="prestador/msg"
										disable-output-escaping="yes" />
								</p>
							</td>
						</tr>

						<tr>
							<td
								style="padding:20px 24px 20px 24px; margin:0px 0px 0px 0px; border-bottom:solid 0px #f5f5f5;">
								<p>
									<a href="{url_cadastro}">Link de Acesso ao Cadastro</a>
								</p>
							</td>
						</tr>

						<tr>
							<td style="padding:20px 24px 20px 24px; margin:0px 0px 0px 0px;">
								<p>
									Atenciosamente,
									<br />
									<br />
									Unimed Curitiba
									<br />
									(41) 3021-7000
								</p>
							</td>
						</tr>

						<tr>
							<td
								style="padding:20px 24px 20px 24px; margin:0px 0px 0px 0px;font-size:10px;">
								<p>
									N&#227;o responda a este e-mail. Esta &#233; uma mensagem
									gerada automaticamente.
									<br />
									Se voc&#234; deseja alterar o recebimento destas
									notifica&#231;&#245;es,
									<a href="{url}">clique aqui</a>
									e acesse o menu de Configura&#231;&#245;es.

								</p>
							</td>
						</tr>

						<tr>
							<td style="padding:20px 0 20px 0;">
							</td>
						</tr>

						<tr>
							<td style="padding:0px 0px 0px 0px; margin:0px 0px 0px 0px;">
								<!-- a href="{site/component/content/url}" target="_blank"><img src="{site/url}img/content/footer_v2.png?limpa=cache_eg" 
									alt="UNIMED Curitiba" title="UNIMED Curitiba" style="border-style:none;" 
									/></a -->
								<img src="{url}/resources/img/footer_v2.png?limpa=cache_eg"
									alt="UNIMED Curitiba" title="UNIMED Curitiba" style="border-style:none;" />
							</td>
						</tr>

					</table>
				</div>
			</body>
		</html>

	</xsl:template>
</xsl:stylesheet>