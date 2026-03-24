
package souk.demo.converter;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class GenericConverter<M, D> {

    private final Function<M, D> toDTO;
    private final Function<D, M> toEntity;

    public GenericConverter(Function<M, D> toDTO, Function<D, M> toEntity) {
        this.toDTO = toDTO;
        this.toEntity = toEntity;
    }

    public D toDTO(M model) {
        return toDTO.apply(model);
    }

    public M toEntity(D dto) {
        return toEntity.apply(dto);
    }

    public List<D> toDTOList(List<M> models) {
        return models.stream()
                .map(toDTO)
                .collect(Collectors.toList());
    }

    public List<M> toEntityList(List<D> dtos) {
        return dtos.stream()
                .map(toEntity)
                .collect(Collectors.toList());
    }
}