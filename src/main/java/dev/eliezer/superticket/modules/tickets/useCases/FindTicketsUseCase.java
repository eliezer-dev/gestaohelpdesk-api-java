package dev.eliezer.superticket.modules.tickets.useCases;

import dev.eliezer.superticket.dto.TicketResponseDTO;
import dev.eliezer.superticket.modules.tickets.repositories.TicketRepository;
import dev.eliezer.superticket.service.exception.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static dev.eliezer.superticket.modules.tickets.mappers.TicketMapper.formatTicketToTicketResponseDTO;

@Service
public class FindTicketsUseCase {

    @Autowired
    private TicketRepository ticketRepository;

    public List<TicketResponseDTO> execute(Long userId, Long type, String search, Long searchType) {
        List<TicketResponseDTO> ticketsList = new ArrayList<>();
        /*
         * type == 0 -> retorna todos os chamados, opção Todos os Chamados no front.
         *
         * */
        //pesquisa todos os ticket
        if (userId == 0 && type == 0) {
            if (searchType != 0 && !search.isEmpty()) {
                //pesquisa por id do ticket
                if (searchType == 1) {
                    String id = search;
                    ticketRepository
                            .findByIdStartingWithAndNotStatusTypeClosed(id)
                            .forEach(ticket -> {
                                ticketsList.add(formatTicketToTicketResponseDTO(ticket));
                            });
                    return ticketsList;
                }
                //pesquisa por razão social do cliente
                if (searchType == 2) {
                    String razaoSocialName = search;
                    ticketRepository.findByClientRazaoSocialNameAndNotStatusTypeClosed(razaoSocialName).
                            forEach(ticket -> {
                                var ticketDTO = formatTicketToTicketResponseDTO(ticket);
                                ticketsList.add(ticketDTO);
                            });

                    return ticketsList;
                }
                //pesquisa cnpj
                if (searchType == 3) {
                    String cpfCnpj = search;
                    ticketRepository.findByCpfCnpjAndNotStatusTypeClosed(cpfCnpj).
                            forEach(ticket -> {
                                var ticketDTO = formatTicketToTicketResponseDTO(ticket);
                                ticketsList.add(ticketDTO);
                            });

                    return ticketsList;
                }
                //pesquisa todos os ticket
            }else {
                ticketRepository.findAllByStatusTypeNotClosed().forEach(ticket -> {
                    var ticketDTO = formatTicketToTicketResponseDTO(ticket);
                    ticketsList.add(ticketDTO);
                });
                return ticketsList;
            }
            //pesquisa por chamados atribuídos ao usuário
        }else if (userId != 0 && type == 0) {
            if (searchType != 0 && !search.isEmpty()) {
                //pesquisa por id do ticket
                if (searchType == 1) {
                    String id = search;
                    ticketRepository.findByIdAndUserIdAndNotStatusTypeClosed(id, userId)
                            .forEach(ticket -> {
                                ticketsList.add(formatTicketToTicketResponseDTO(ticket));
                            });
                    return ticketsList;
                }
                //pesquisa por razão social do cliente
                if (searchType == 2) {
                    ticketRepository.findByUserIdAndClientRazaoSocialNameAndNotStatusTypeClosed(userId, search).
                            forEach(ticket -> {
                                var ticketDTO = formatTicketToTicketResponseDTO(ticket);
                                ticketsList.add(ticketDTO);});

                    return ticketsList;
                }
                //pesquisa cnpj
                if (searchType == 3) {
                    ticketRepository.findByUserIdAndCpfCnpjAndNotStatusTypeClosed(userId, search).
                            forEach(ticket -> {
                                var ticketDTO = formatTicketToTicketResponseDTO(ticket);
                                ticketsList.add(ticketDTO);});

                    return ticketsList;
                }
                //consulta todos os chamados atribuído ao usuário
            } else {
                ticketRepository.findByUserIdAndNotStatusTypeClosed(userId).forEach(ticket -> {
                    var ticketDTO = formatTicketToTicketResponseDTO(ticket);
                    ticketsList.add(ticketDTO);
                });
                return ticketsList;
            }

            //pesquisa por todos os chamados atribuídos a outros usuários
        }else if (type == 1) {
            if (userId == 0) {
                throw new BusinessException("user id is not provided");
            }
            if (searchType != 0 && !search.isEmpty()) {
                //pesquisa por id do ticket
                if (searchType == 1) {
                    String id = search;
                    ticketRepository.findByNotUserIdAndIdAndNotStatusTypeClosed(userId, id)
                            .forEach(ticket -> {
                                ticketsList.add(formatTicketToTicketResponseDTO(ticket));
                            });
                    return ticketsList;
                }
                //pesquisa por razão social do cliente
                if (searchType == 2) {
                    ticketRepository.findByNotUserIdAndClientRazaoSocialNameAndNotStatusTypeClosed(userId, search).
                            forEach(ticket -> {
                                var ticketDTO = formatTicketToTicketResponseDTO(ticket);
                                ticketsList.add(ticketDTO);});

                    return ticketsList;
                }
                //pesquisa por cnpj
                if (searchType == 3) {
                    ticketRepository.findByNotUserIdAndCpfCnpjAndNotStatusTypeClosed(userId, search).
                            forEach(ticket -> {
                                var ticketDTO = formatTicketToTicketResponseDTO(ticket);
                                ticketsList.add(ticketDTO);});

                    return ticketsList;
                }
                //pesquisa por todos os chamados atribuídos a outros usuários
            }else {
                ticketRepository.findByNotUserIdAndNotStatusTypeClosed(userId).forEach(ticket -> {
                    var ticketDTO = formatTicketToTicketResponseDTO(ticket);
                    ticketsList.add(ticketDTO);
                });
                return ticketsList;
            }
            //pesquisa por todos os chamados sem atribuição de usuário.
        }else if (type == 2) {
            if (searchType != 0 && !search.isEmpty()) {
                //pesquisa por id do ticket
                if (searchType == 1) {
                    String id = search;
                    ticketRepository.findByIdAndTicketsWithoutUserAndNotStatusTypeClosed(id)
                            .forEach(ticket -> {
                                ticketsList.add(formatTicketToTicketResponseDTO(ticket));
                            });
                    return ticketsList;
                }
                //pesquisa por razão social do cliente
                if (searchType == 2) {
                    ticketRepository.findByTicketsWithoutUserAndClientRazaoSocialNameAndNotStatusTypeClosed(search).
                            forEach(ticket -> {
                                var ticketDTO = formatTicketToTicketResponseDTO(ticket);
                                ticketsList.add(ticketDTO);
                            });

                    return ticketsList;
                }
                //pesquisa cnpj
                if (searchType == 3) {
                    ticketRepository.findByTicketsWithoutUserAndCpfCnpjAndNotStatusTypeClosed(search).
                            forEach(ticket -> {
                                var ticketDTO = formatTicketToTicketResponseDTO(ticket);
                                ticketsList.add(ticketDTO);
                            });

                    return ticketsList;
                }
                //pesquisa por todos os chamados sem atribuição de usuário
            } else {
                ticketRepository.findByTicketsWithoutUserAndNotStatusTypeClosed().forEach(ticket -> {
                    var ticketDTO = formatTicketToTicketResponseDTO(ticket);
                    ticketsList.add(ticketDTO);
                });
                return ticketsList;
            }

            //pesquisa por chamados concluídos.
        }else if (type == 3) {
            if (searchType != 0 && !search.isEmpty()) {
                //pesquisa por id do ticket
                if (searchType == 1) {
                    String id = search;
                    ticketRepository.findAllCompletedTicketsById(id)
                            .forEach(ticket -> {
                                ticketsList.add(formatTicketToTicketResponseDTO(ticket));
                            });
                    return ticketsList;
                }
                //pesquisa por razão social do cliente
                if (searchType == 2) {
                    ticketRepository.findAllCompletedTicketsByClientRazaoSocialName(search).
                            forEach(ticket -> {
                                var ticketDTO = formatTicketToTicketResponseDTO(ticket);
                                ticketsList.add(ticketDTO);
                            });

                    return ticketsList;
                }
                //pesquisa cnpj
                if (searchType == 3) {
                    ticketRepository.findAllCompletedTicketsByCpfCnpj(search).
                            forEach(ticket -> {
                                var ticketDTO = formatTicketToTicketResponseDTO(ticket);
                                ticketsList.add(ticketDTO);
                            });

                    return ticketsList;
                }
                //pesquisa por todos os chamados sem atribuição de usuário
            } else {
                ticketRepository.findAllCompletedTickets().forEach(ticket -> {
                    var ticketDTO = formatTicketToTicketResponseDTO(ticket);
                    ticketsList.add(ticketDTO);
                });
                return ticketsList;
            }
        }else if (type == 4) {
            if (searchType != 0 && !search.isEmpty()) {
                //pesquisa por id do ticket
                if (searchType == 1) {
                    String id = search;
                    ticketRepository.findAllClosedTicketsById(id)
                            .forEach(ticket -> {
                                ticketsList.add(formatTicketToTicketResponseDTO(ticket));
                            });
                    return ticketsList;
                }
                //pesquisa por razão social do cliente
                if (searchType == 2) {
                    ticketRepository.findAllClosedTicketsByClientRazaoSocialName(search).
                            forEach(ticket -> {
                                var ticketDTO = formatTicketToTicketResponseDTO(ticket);
                                ticketsList.add(ticketDTO);
                            });

                    return ticketsList;
                }
                //pesquisa cnpj
                if (searchType == 3) {
                    ticketRepository.findAllClosedTicketsByCpfCnpj(search).
                            forEach(ticket -> {
                                var ticketDTO = formatTicketToTicketResponseDTO(ticket);
                                ticketsList.add(ticketDTO);
                            });

                    return ticketsList;
                }
                //pesquisa por todos os chamados sem atribuição de usuário
            } else {
                ticketRepository.findAllClosedTickets().forEach(ticket -> {
                    var ticketDTO = formatTicketToTicketResponseDTO(ticket);
                    ticketsList.add(ticketDTO);
                });
                return ticketsList;
            }

        }else {
            throw new BusinessException("invalid option.");
        }
        return ticketsList;
    }
}
