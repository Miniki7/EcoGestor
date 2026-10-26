package api.mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import api.dto.IndicadorAmbientalRequestDTO;
import api.dto.IndicadorAmbientalResponseDTO;
import api.model.IndicadorAmbiental;
import api.model.Usuario;

@Component
public class IndicadorAmbientalMapper {

    // DTO de entrada + usuário já resolvido pelo Service -> entidade nova (id null).
    public IndicadorAmbiental toEntity(IndicadorAmbientalRequestDTO dto, Usuario usuario) {
        return new IndicadorAmbiental(
                usuario,
                dto.consumoAgua(),
                dto.consumoEnergia(),
                dto.residuosGerados(),
                dto.residuosReciclados(),
                dto.mesReferencia(),
                dto.observacao());
    }

    // Entidade -> DTO de saída (o relacionamento sai como id).
    public IndicadorAmbientalResponseDTO toResponse(IndicadorAmbiental entidade) {
        return new IndicadorAmbientalResponseDTO(
                entidade.getId(),
                entidade.getUsuario().getId(),
                entidade.getMesReferencia(),
                entidade.getConsumoAgua(),
                entidade.getConsumoEnergia(),
                entidade.getResiduosGerados(),
                entidade.getResiduosReciclados(),
                entidade.getObservacao());
    }

    // Converte a lista inteira.
    public List<IndicadorAmbientalResponseDTO> toResponseList(List<IndicadorAmbiental> entidades) {
        return entidades.stream().map(this::toResponse).toList();
    }
}