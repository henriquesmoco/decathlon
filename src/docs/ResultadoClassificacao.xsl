<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

<xsl:template match="/">
  <html>
  <body>
  <h2>Classificacao Decathlon</h2>
    <table border="1">
      <tr bgcolor="#9acd32">
        <th>Posicao</th>
        <th>Nome</th>
        <th>Pontuacao</th>
        <th>100m</th>
        <th>Salto Distância</th>
        <th>Arremesso</th>
        <th>Salto Altura</th>
        <th>400m</th>
        <th>110m Barreiras</th>
        <th>Lançamento Disco</th>
        <th>Salto Com Vara</th>
        <th>Lançamento Dardo</th>
        <th>1500m</th>        
      </tr>
      <xsl:for-each select="decathlon/classificacao">
      <tr>
        <td><xsl:value-of select="posicao"/></td>
        <td><xsl:value-of select="nome"/></td>
        <td><xsl:value-of select="pontuacao"/></td>
        <xsl:for-each select="performances/performance">   
                <td><xsl:value-of select="text()"/></td>                      
        </xsl:for-each>
      </tr>
      </xsl:for-each>
    </table>
  </body>
  </html>
</xsl:template>
</xsl:stylesheet>