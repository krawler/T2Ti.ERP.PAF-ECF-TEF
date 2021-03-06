{*******************************************************************************
Title: T2Ti ERP
Description: Classe de controle do DAV.

The MIT License

Copyright: Copyright (C) 2010 T2Ti.COM

Permission is hereby granted, free of charge, to any person
obtaining a copy of this software and associated documentation
files (the "Software"), to deal in the Software without
restriction, including without limitation the rights to use,
copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the
Software is furnished to do so, subject to the following
conditions:

The above copyright notice and this permission notice shall be
included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES
OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
OTHER DEALINGS IN THE SOFTWARE.

The author may be contacted at:
t2ti.com@gmail.com</p>

@author Albert Eije (T2Ti.COM)
@version 1.0
*******************************************************************************}
unit DAVController;

interface

uses
  Classes, SQLExpr, SysUtils, Generics.Collections, DB, DAVVO;

type
  TDAVController = class
  protected
  public
    function ListaDAVPeriodo(DataInicio:String; DataFim:String): TObjectList<TDAVVO>;
  end;

implementation

uses UDataModule;

var
  ConsultaSQL: String;
  Query: TSQLQuery;

function TDAVController.ListaDAVPeriodo(DataInicio:String; DataFim:String): TObjectList<TDAVVO>;
var
  ListaDAV: TObjectList<TDAVVO>;
  DAVCabecalho: TDAVVO;
  TotalRegistros: Integer;
begin
  ConsultaSQL :=
    'select count(*) AS TOTAL from ECF_DAV_CABECALHO where SITUACAO = "E" and DATA_HORA_EMISSAO between ' +
    QuotedStr(DataInicio) + ' and ' + QuotedStr(DataFim);
  try
    try
      Query := TSQLQuery.Create(nil);
      Query.SQLConnection := FDataModule.Conexao;
      Query.sql.Text := ConsultaSQL;
      Query.Open;
      TotalRegistros := Query.FieldByName('TOTAL').AsInteger;

      if TotalRegistros > 0 then
      begin
        ListaDAV := TObjectList<TDAVVO>.Create;
        ConsultaSQL :=
          'select * from ECF_DAV_CABECALHO where SITUACAO = "E" and DATA_HORA_EMISSAO between ' +
          QuotedStr(DataInicio) + ' and ' + QuotedStr(DataFim);
        Query.sql.Text := ConsultaSQL;
        Query.Open;
        Query.First;
        while not Query.Eof do
        begin
          DAVCabecalho := TDAVVO.Create;
          DAVCabecalho.Id := Query.FieldByName('ID').AsInteger;
          DAVCabecalho.DataHoraEmissao := Query.FieldByName('DATA_HORA_EMISSAO').AsString;
          DAVCabecalho.Valor := Query.FieldByName('VALOR').AsFloat;
          ListaDAV.Add(DAVCabecalho);
          Query.next;
        end;
        result := ListaDAV;
      end
      //caso n�o exista a relacao, retorna um ponteiro nulo
      else
        result := nil;
    except
      result := nil;
    end;
  finally
    Query.Free;
  end;
end;

end.
