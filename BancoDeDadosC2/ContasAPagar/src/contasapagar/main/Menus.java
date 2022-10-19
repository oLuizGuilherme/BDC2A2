package contasapagar.main;

import java.math.BigDecimal;
import java.math.MathContext;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import contasapagar.dao.ContaDAO;
import contasapagar.dao.ParcelaDAO;
import contasapagar.modelo.Conta;
import contasapagar.modelo.Parcela;
import contasapagar.service.RelatoriosService;

public class Menus {

	public static RelatoriosService relatoriosService = new RelatoriosService();

	public static void main(String[] args) {

		InOut.msgDeInformacao("Trabalho de Banco de Dados",
				"NOME DOS COMPONENTES DO GRUPO                  \nLuana Amy Nakasuga\nLuiz Guilherme Machado Zortéa\nMatheus Silva Herculino\nRenato Archanjo Rabello\nVictor Isida Hirosse");

		String menu = "1 - Relatórios.\n2 - Inserir registros.\n3 - Remover registros.\n4 - Atualizar registros.\n5 - Sair.";
		int opcao = 0;

		do {

			opcao = InOut.leInt(menu);

			switch (opcao) {

			case 1:
				mostrarRelatorios();
				break;

			case 2:
				inserirRegistros();
				break;

			case 3:
				removerRegistros();
				break;

			case 4:
				atualizarRegistros();
				break;

			case 5:
				break;

			default:
				break;
			}

		} while (opcao != 5);
	}

	private static void mostrarRelatorios() {

		String menu1 = "Selecione um relatório a ser exibido.\n1 - Valor total a receber.\n2 - Valor total a pagar por Conta.\n3 - Voltar. ";
		int opcao1 = 0;
		do {
			opcao1 = InOut.leInt(menu1);
			switch (opcao1) {
			case 1:
				BigDecimal valor = relatoriosService.valorTotalAReceber();
				InOut.msgDeInformacao("VALOR TOTAL A RECEBER", "O valor total a receber de todas as contas é de R$"
						+ (valor != null ? valor.round(MathContext.DECIMAL32) : BigDecimal.valueOf(0)));
				break;

			case 2:
				int id = InOut.leInt("Informe o id da conta que deseja verificar as parcelas em aberto.");
				String parcelasEmAberto = relatoriosService.parcelasEmAberto(id);

				if (parcelasEmAberto.length() == 0) {
					InOut.msgDeInformacao("PARCELAS EM ABERTO",
							"Não existe nenhuma parcela em aberto para conta selecionada.");
					break;
				}

				InOut.msgDeInformacao("PARCELAS EM ABERTO", parcelasEmAberto);
				break;

			case 3:
				break;

			default:
				break;
			}

		} while (opcao1 != 3);

	}

	private static void inserirRegistros() {

		String menu2 = "1 - Inserir contas.\n2 - Inserir parcelas.\n3 - Voltar. ";
		int opcao2 = 0;
		do {
			opcao2 = InOut.leInt(menu2);
			switch (opcao2) {
			case 1:
				Conta conta = new Conta();
				conta.setTipo(InOut.leInt("Informe o tipo de conta\n1 - Conta a Pagar\n2 - Conta a Receber"));
				String dataQuitacao = InOut.leString(
						"Informe a data de quitação da nova conta ou X para conta ainda a quitar.\nPor favor siga o padrão DD-MM-AAAA");
				if (dataQuitacao.equalsIgnoreCase("X")) {
					conta.setDataLiquidacao(null);
				} else {
					Integer anoQuitacao = Integer.valueOf(dataQuitacao.substring(6, 10));
					Integer mesQuitacao = Integer.valueOf(dataQuitacao.substring(3, 5));
					Integer diaQuitacao = Integer.valueOf(dataQuitacao.substring(0, 2));
					conta.setDataLiquidacao(Date.valueOf(LocalDate.of(anoQuitacao, mesQuitacao, diaQuitacao)));
				}
			
				try {
					new ContaDAO().inserir(conta);
					InOut.msgDeInformacao("INSERIR CONTA", "Uma nova conta foi inserida com sucesso.");
				} catch (RuntimeException e) {
					InOut.msgDeErro(e.getCause().toString(), e.getMessage());
				}
				break;

			case 2:
				Parcela parcela = new Parcela();
				parcela.setValor(BigDecimal.valueOf(InOut.leDouble("Informe o valor da nova parcela")));
				String data = InOut
						.leString("Informe a data de vencimento da nova parcela.\nPor favor siga o padrão DD-MM-AAAA");
				Integer ano = Integer.valueOf(data.substring(6, 10));
				Integer mes = Integer.valueOf(data.substring(3, 5));
				Integer dia = Integer.valueOf(data.substring(0, 2));
				parcela.setDataVencimento(Date.valueOf(LocalDate.of(ano, mes, dia)));
				parcela.setIdConta(InOut.leInt(new ContaDAO().listar() + "\nInforme o id da conta que esta parcela pertence."));
				parcela.setNumeroParcela(InOut.leInt("Informe o número da nova parcela."));
				data = InOut.leString(
						"Informe a data de pagamento da nova parcela ou X para parcela ainda a pagar.\nPor favor siga o padrão DD-MM-AAAA");
				if (data.equalsIgnoreCase("X")) {
					parcela.setDataPagamento(null);
				} else {
					ano = Integer.valueOf(data.substring(6, 10));
					mes = Integer.valueOf(data.substring(3, 5));
					dia = Integer.valueOf(data.substring(0, 2));
					parcela.setDataPagamento(Date.valueOf(LocalDate.of(ano, mes, dia)));
				}

				try {
					new ParcelaDAO().inserir(parcela);
					InOut.msgDeInformacao("INSERIR PARCELA", "Uma nova parcela foi inserida com sucesso.");
				} catch (RuntimeException e) {
					InOut.msgDeErro(e.getCause().toString(), e.getMessage());
				}

				break;

			case 3:
				break;

			default:
				break;
			}

		} while (opcao2 != 3);
	}

	private static void removerRegistros() {

		String menu3 = "1 - Remover contas.\n2 - Remover parcelas.\n3 - Voltar. ";
		int opcao3 = 0;
		ContaDAO contaDAO = new ContaDAO();
		ParcelaDAO parcelaDAO = new ParcelaDAO();
		do {
			opcao3 = InOut.leInt(menu3);
			switch (opcao3) {
			case 1:
				InOut.msgDeInformacao("REMOVER CONTAS", contaDAO.listar());

				int id = InOut.leInt("Informe o id da Conta que deseja excluir ou 0 para cancelar.");

				if (InOut.leInt("Deseja realmente excluir o registro de id " + id + "?\n1 - Sim\n2 - Não") == 2)
					break;

				try {
					Conta buscar = contaDAO.buscar(id);
					if (buscar != null) {

						List<Parcela> parcelasDaConta = parcelaDAO.buscarPorConta(id);
						if (parcelasDaConta.size() > 0) {
							if (InOut.leInt("Existem " + parcelasDaConta.size()
									+ " parcelas referentes a essa conta.\nDeseja exlcuir todas parcelas?\n1 - Sim\n2 - Não") == 1) {
								parcelasDaConta.forEach(p -> parcelaDAO.remover(p.getId()));
							} else {
								InOut.msgDeInformacao("REMOVER CONTAS", "Nenhum registro será excluido.");
								break;
							}
						}

						contaDAO.remover(id);
						InOut.msgDeInformacao("REMOVER CONTA", "A conta com o id " + id + " foi removida com sucesso.");
					} else {
						InOut.msgDeInformacao("REMOVER CONTA", "Não existe um registro de conta com o id informado.");
					}
				} catch (RuntimeException e) {
					InOut.msgDeErro(e.getCause().toString(), e.getMessage());
				}
				break;

			case 2:
				InOut.msgDeInformacao("REMOVER PARCELAS", parcelaDAO.listar());

				int idParcela = InOut.leInt("Informe o id da Parcela que deseja excluir ou 0 para cancelar.");

				if (idParcela == 0 || InOut.leInt("Deseja realmente excluir o registro de id " + idParcela + "?\n1 - Sim\n2 - Não") == 2)
					break;

				try {
					Parcela buscar = parcelaDAO.buscar(idParcela);
					if (buscar != null) {
						parcelaDAO.remover(idParcela);
						InOut.msgDeInformacao("REMOVER PARCELA",
								"A parcela com o id " + idParcela + " foi removida com sucesso.");
					} else {
						InOut.msgDeInformacao("REMOVER PARCELA",
								"Não existe um registro de parcela com o id informado.");
					}
				} catch (RuntimeException e) {
					InOut.msgDeErro(e.getCause().toString(), e.getMessage());
				}
				break;

			case 3:
				break;

			default:
				break;
			}

		} while (opcao3 != 3);

	}

	private static void atualizarRegistros() {

		String menu4 = "1 - Atualizar conta.\n2 - Atualizar parcelas.\n3 - Voltar. ";
		int opcao4 = 0;
		do {

			opcao4 = InOut.leInt(menu4);
			ContaDAO contaDAO = new ContaDAO();
			ParcelaDAO parcelaDAO = new ParcelaDAO();

			switch (opcao4) {
			case 1:
				InOut.msgDeInformacao("ATUALIZAR CONTAS", contaDAO.listar());

				int id = InOut.leInt("Informe o id da Conta que deseja atualizar ou 0 para cancelar.");

				if (id == 0)
					break;

				try {
					Conta conta = contaDAO.buscar(id);
					if (conta != null) {
						conta.setTipo(InOut.leInt("Informe o tipo de conta\n1 - Conta a Pagar\n2 - Conta a Receber"));
						String dataQuitacao = InOut.leString(
								"Informe a data de quitação da conta ou X para conta ainda a quitar.\nPor favor siga o padrão DD-MM-AAAA");
						Integer anoQuitacao = Integer.valueOf(dataQuitacao.substring(6, 10));
						Integer mesQuitacao = Integer.valueOf(dataQuitacao.substring(3, 5));
						Integer diaQuitacao = Integer.valueOf(dataQuitacao.substring(0, 2));
						conta.setDataLiquidacao(Date.valueOf(LocalDate.of(anoQuitacao, mesQuitacao, diaQuitacao)));

						contaDAO.alterar(conta);
						InOut.msgDeInformacao("ATUALIZAR CONTA", "Uma conta foi atualizada com sucesso.");
					} else {
						InOut.msgDeInformacao("ATUALIZAR CONTA", "Não existe um registro de conta com o id informado.");
					}
				} catch (RuntimeException e) {
					InOut.msgDeErro(e.getCause().toString(), e.getMessage());
				}

				break;

			case 2:
				InOut.msgDeInformacao("ATUALIZAR PARCELAS", parcelaDAO.listar());
				int idParcela = InOut.leInt("Informe o id da Parcela que deseja atualizar ou 0 para cancelar.");
				if (idParcela == 0)
					break;
				try {
					Parcela parcela = parcelaDAO.buscar(idParcela);
					
					if (parcela == null) {
						InOut.msgDeErro("ATUALIZAR PARCELAS", "Não existe parcela com o id informado");
						break;
					}
					
					parcela.setValor(BigDecimal.valueOf(InOut.leDouble("Informe o valor da parcela")));
					String data = InOut
							.leString("Informe a data de vencimento da parcela.\nPor favor siga o padrão DD-MM-AAAA");
					Integer ano = Integer.valueOf(data.substring(6, 10));
					Integer mes = Integer.valueOf(data.substring(3, 5));
					Integer dia = Integer.valueOf(data.substring(0, 2));
					parcela.setDataVencimento(Date.valueOf(LocalDate.of(ano, mes, dia)));
					parcela.setIdConta(InOut.leInt(contaDAO.listar() + "\nInforme o id da conta que esta parcela pertence."));
					parcela.setNumeroParcela(InOut.leInt("Informe o número da nova parcela."));
					data = InOut.leString(
							"Informe a data de pagamento da parcela ou X para parcela ainda a pagar.\nPor favor siga o padrão DD-MM-AAAA");
					if (data.equalsIgnoreCase("X")) {
						parcela.setDataPagamento(null);
					} else {
						ano = Integer.valueOf(data.substring(6, 10));
						mes = Integer.valueOf(data.substring(3, 5));
						dia = Integer.valueOf(data.substring(0, 2));
						parcela.setDataPagamento(Date.valueOf(LocalDate.of(ano, mes, dia)));
					}

					parcelaDAO.alterar(parcela);
					InOut.msgDeInformacao("ATUALIZAR PARCELA", "Uma parcela foi atualizada com sucesso.");
				} catch (RuntimeException e) {
					InOut.msgDeErro(e.getCause().toString(), e.getMessage());
				}

				break;

			case 3:
				break;

			default:
				break;
			}

		} while (opcao4 != 3);

	}

}
