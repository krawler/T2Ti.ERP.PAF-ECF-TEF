{*******************************************************************************
Title: T2Ti ERP
Description: Datamodule.

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
unit UDataModule;

interface

uses
SysUtils, Forms, ACBrDevice, ACBrBase, ACBrECF, DBXMySql, FMTBcd,
Provider, DBClient, DB, SqlExpr, Classes, WideStrings, StdCtrls, Controls,
Windows, ACBrUtil, dateutils, strutils, dialogs,
ACBrPAF, ACBrPAF_D, ACBrPAF_E, ACBrPAF_P, ACBrPAF_R, ACBrPAF_T, ACBrPAFRegistros,
Math, ACBrSpedFiscal, ACBrSintegra;

type
  TFDataModule = class(TDataModule)
    ACBrECF: TACBrECF;
    ACBrPAF: TACBrPAF;
    ACBrSintegra: TACBrSintegra;
    ACBrSPEDFiscal: TACBrSPEDFiscal;
    Conexao: TSQLConnection;
    procedure ACBrPAFPAFCalcEAD(Arquivo: string);
    procedure DataModuleCreate(Sender: TObject);
  private
    { Private declarations }
  public
    { Public declarations }
    IdEmpresa : String;
    IdCaixa : String;
    IdOperador : String;
    IdImpressora : String;
  end;

var
  FDataModule: TFDataModule;

implementation

uses UPAF, USintegra, USpedFiscal;

{$R *.dfm}

procedure TFDataModule.ACBrPAFPAFCalcEAD(Arquivo: string);
begin
//
end;

procedure TFDataModule.DataModuleCreate(Sender: TObject);
begin
  FDataModule.IdEmpresa := ParamStr(2);
  FDataModule.IdCaixa := ParamStr(3);
  FDataModule.IdOperador := ParamStr(4);
  FDataModule.IdImpressora := ParamStr(5);
  FDataModule.ACBrECF.Porta := ParamStr(6);
  FDataModule.ACBrECF.TimeOut := StrToInt(ParamStr(7));
  FDataModule.ACBrECF.IntervaloAposComando := StrToInt(ParamStr(8));
  FDataModule.ACBrECF.Modelo := TACBrECFModelo(StrToInt(ParamStr(9)));

  FDataModule.ACBrECF.Ativar;
  FDataModule.ACBrECF.CarregaAliquotas;
  FDataModule.ACBrECF.CarregaFormasPagamento;
  if ParamStr(1) = 'geraMovimentoECF' then
    UPaf.GeraMovimentoECF
  else if ParamStr(1) = 'geraTabelaProdutos' then
    UPaf.GeraTabelaProdutos
  else if ParamStr(1) = 'geraArquivoEstoque' then
    UPaf.GeraArquivoEstoque
  else if ParamStr(1) = 'DAVEmitidos' then
    UPaf.DAVEmitidos(ParamStr(10),ParamStr(11))
  else if ParamStr(1) = 'gerarArquivoSintegra' then
    USintegra.GerarArquivoSintegra(ParamStr(10),ParamStr(11))
  else if ParamStr(1) = 'gerarArquivoSpedFiscal' then
    USpedFiscal.GerarArquivoSpedFiscal(ParamStr(10),ParamStr(11));
  FDataModule.ACBrECF.Desativar;
end;

end.
