/**
 * <p>Title: T2Ti ERP</p>
 * <p>Description: PAF-ECF + TEF - Regras de negócio/persistência dos Valores pagos
 * em determinada venda com os respectivos tipos de pagamento.</p>
 *
 * <p>The MIT License</p>
 *
 * <p>Copyright: Copyright (C) 2010 T2Ti.COM</p>
 *
 * Permission is hereby granted, free of charge, to any person
 * obtaining a copy of this software and associated documentation
 * files (the "Software"), to deal in the Software without
 * restriction, including without limitation the rights to use,
 * copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the
 * Software is furnished to do so, subject to the following
 * conditions:
 *
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES
 * OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
 * OTHER DEALINGS IN THE SOFTWARE.
 *
 *       The author may be contacted at:
 *           t2ti.com@gmail.com</p>
 *
 * @author Albert Eije (T2Ti.COM)
 * @version 1.0
 */
package com.t2tierp.pafecf.controller;

import com.t2tierp.pafecf.bd.AcessoBanco;
import com.t2tierp.pafecf.view.Caixa;
import com.t2tierp.pafecf.vo.MeiosPagamentoVO;
import com.t2tierp.pafecf.vo.R07VO;
import com.t2tierp.pafecf.vo.TotalTipoPagamentoVO;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class TotalTipoPagamentoController {

    String consultaSQL;
    Statement stm;
    PreparedStatement pstm;
    ResultSet rs;
    AcessoBanco bd = new AcessoBanco();

    public void gravaTotaisVenda(ArrayList<TotalTipoPagamentoVO> pListaTotalTipoPagamento) {

        TotalTipoPagamentoVO totalTipoPagamento;

        try {
            for (int i = 0; i < pListaTotalTipoPagamento.size(); i++) {
                totalTipoPagamento = pListaTotalTipoPagamento.get(i);
                consultaSQL =
                        "insert into ECF_TOTAL_TIPO_PGTO (ID_ECF_VENDA_CABECALHO,ID_ECF_TIPO_PAGAMENTO,VALOR,NSU) "
                        + "values (?, ?, ?, ?)";
                pstm = bd.conectar().prepareStatement(consultaSQL);
                pstm.setInt(1, totalTipoPagamento.getIdVendaCabecalho());
                pstm.setInt(2, totalTipoPagamento.getTipoPagamentoVO().getId());
                pstm.setDouble(3, totalTipoPagamento.getValor());
                if (totalTipoPagamento.getNSU() != null) {
                    pstm.setString(4, totalTipoPagamento.getNSU());
                } else {
                    pstm.setNull(4, java.sql.Types.VARCHAR);
                }
                pstm.executeUpdate();

                //TODO : Esse é o melhor lugar para gerar o R07?
                R07VO R07 = new R07VO();
                try {
                    R07.setCCF(Integer.valueOf(Caixa.ACBrECF.getNumCCF()));
                } catch (Throwable t) {
                }
                R07.setMeioPagamento(totalTipoPagamento.getTipoPagamentoVO().getDescricao());
                R07.setValorPagamento(totalTipoPagamento.getValor());
                //TODO : Como fazer o controle dos estornos?
                R07.setIndicadorEstorno("N");
                R07.setValorEstorno(0.0);
                RegistroRController RegistroRControl = new RegistroRController();
                RegistroRControl.gravaR07(R07);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            bd.desconectar();
        }
    }

    public List<MeiosPagamentoVO> meiosPagamento(String pDataInicio, String pDataFim, Integer pIdImpressora) {
        consultaSQL =
                "SELECT V.DATA_HORA_VENDA, M.ID_ECF_IMPRESSORA, P.DESCRICAO, SUM(TP.VALOR) AS TOTAL "
                + "FROM "
                + "ECF_VENDA_CABECALHO V, ECF_MOVIMENTO M, ECF_TIPO_PAGAMENTO P, ECF_TOTAL_TIPO_PGTO TP "
                + "WHERE "
                + "V.ID_ECF_MOVIMENTO = M.ID AND "
                + "TP.ID_ECF_VENDA_CABECALHO=V.ID AND "
                + "TP.ID_ECF_TIPO_PAGAMENTO = P.ID AND "
                + "M.ID_ECF_IMPRESSORA = " + pIdImpressora + " AND "
                + "(V.DATA_HORA_VENDA BETWEEN '" + pDataInicio + "' and '" + pDataFim
                + "') GROUP BY "
                + "P.DESCRICAO,V.DATA_HORA_VENDA,M.ID_ECF_IMPRESSORA";

        try {
            List<MeiosPagamentoVO> listaMeiosPagamento = new ArrayList<MeiosPagamentoVO>();

            stm = bd.conectar().createStatement();
            rs = stm.executeQuery(consultaSQL);
            rs.beforeFirst();
            while (rs.next()) {
                MeiosPagamentoVO meiosPagamento = new MeiosPagamentoVO();
                meiosPagamento.setDescricao(rs.getString("DESCRICAO"));
                meiosPagamento.setDataHora(rs.getDate("DATA_HORA_VENDA"));
                meiosPagamento.setTotal(rs.getDouble("TOTAL"));
                listaMeiosPagamento.add(meiosPagamento);
            }
            return listaMeiosPagamento;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            bd.desconectar();
        }
    }
}
