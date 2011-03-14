/**
 * <p>Title: T2Ti ERP</p>
 * <p>Description: Classe que será utilizada como modelo da Tabela.</p>
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
package com.t2tierp.pafecf.infra;

import com.t2tierp.pafecf.vo.TipoPagamentoVO;
import java.util.List;
import javax.swing.table.AbstractTableModel;

public class EfetuaPagamentoTableModel extends AbstractTableModel {

    private List<TipoPagamentoVO> listaTipoPagamento;

    public EfetuaPagamentoTableModel(List<TipoPagamentoVO> listaTipoPagamento) {
        this.listaTipoPagamento = listaTipoPagamento;
    }

    /**
     * Obtem o valor na linha e coluna.
     * @see javax.swing.table.TableModel#getValueAt(int, int)
     */
    public Object getValueAt(int rowIndex, int columnIndex) {
        TipoPagamentoVO tipoPagamento = listaTipoPagamento.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return tipoPagamento.getDescricao();
            case 1:
                return tipoPagamento.getValor();
        }
        return null;
    }

    /**
     * Retorna o numero de linhas no modelo.
     * @see javax.swing.table.TableModel#getRowCount()
     */
    public int getRowCount() {
        return listaTipoPagamento.size();
    }

    /**
     * Retorna o numero de colunas no modelo.
     * @see javax.swing.table.TableModel#getColumnCount()
     */
    public int getColumnCount() {
        return 2;
    }

    public TipoPagamentoVO getValues(int rowIndex) {
        return listaTipoPagamento.get(rowIndex);
    }

    public boolean isCellEditable(int row, int col) {
        //informa as colunas que não desejamos edição
        if (col == 0) {
            return false;
        } else {
            return true;
        }
    }

    public void setValueAt(Object value, int row, int col) {
        //TODO : o que pode ocorrer de errado aqui?
        if (value.toString().equals("")) {
            value = "0.00";
        }
        listaTipoPagamento.get(row).setValor(Double.valueOf(value.toString()));
        fireTableCellUpdated(row, col);
    }
}
