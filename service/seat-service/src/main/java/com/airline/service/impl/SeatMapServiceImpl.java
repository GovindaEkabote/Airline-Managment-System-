package com.airline.service.impl;

import com.airline.exception.ResourceNotFoundException;
import com.airline.mapper.SeatMapMapper;
import com.airline.model.CabinClass;
import com.airline.model.SeatGenerationRequest;
import com.airline.model.SeatMap;
import com.airline.playload.request.SeatMapRequest;
import com.airline.playload.response.SeatMapResponse;
import com.airline.repository.CabinClassRepository;
import com.airline.repository.SeatMapRepository;
import com.airline.service.SeatMapService;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.apache.coyote.BadRequestException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SeatMapServiceImpl implements SeatMapService {

    private static final String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    private final SeatMapRepository seatMapRepository;
    private final CabinClassRepository  cabinClassRepository;


    @Override
    public SeatMapResponse create(Long airlineId, SeatMapRequest request) throws Exception {

        validateLayout(request);
        CabinClass cabinClass = cabinClassRepository.findById(request.getCabinClassId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Cabin Id not found: " + request.getCabinClassId()
                        )
                );

        if (seatMapRepository.existsByAirlineIdAndCabinClassId(
                airlineId,
                request.getCabinClassId()
        )) {
            throw new Exception("Cabin class already exists for the given airline");
        }

        SeatMap seatMap = SeatMapMapper.toEntity(request, cabinClass);

        seatMap.setAirlineId(airlineId);

        SeatMap savedSeatMap = seatMapRepository.save(seatMap);
        // toDo : generate seat for seat map
        return SeatMapMapper.toResponse(savedSeatMap);
    }

    @Override
    @Transactional(readOnly = true)
    public SeatMapResponse getSeatMapById(Long id) {
        SeatMap seatMap = seatMapRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not Found")
                );
        return SeatMapMapper.toResponse(seatMap);
    }

    @Override
    public SeatMapResponse getSeatMapCabinClass(Long cabinClassId) {
        SeatMap map = seatMapRepository.findByCabinClassId(cabinClassId)
                .orElseThrow(() -> new ResourceNotFoundException("SeatMap for cabinClass"+ cabinClassId));
        return SeatMapMapper.toResponse(map);
    }

    @Override
    public Page<SeatMapResponse> getByAirlineId(Long airlineId, Pageable pageable) {
        return null;
    }

    @Override
    public SeatMapResponse updateSeatMap(Long id, SeatMapRequest request) {
        SeatMap seatMap = seatMapRepository.findById(id)
                .orElseThrow(
                        () -> new ResourceNotFoundException("Seat Map not found with " + id)
                );
        SeatMapMapper.updateEntity(request,seatMap);
        SeatMap updated=seatMapRepository.save(seatMap);
        return SeatMapMapper.toResponse(updated);
    }

    @Override
    public void delete(Long id) {
        SeatMap seatMap = seatMapRepository.findById(id)
                .orElseThrow(
                        () -> new ResourceNotFoundException("Seat Map not found with " + id)
                );
        seatMapRepository.delete(seatMap);
    }



    private void validateLayout(SeatMapRequest request) throws Exception{
        int perRow = request.getLeftSeatsPerRow() + request.getRightSeatsPerRow();
        if (perRow < 1){
            throw  new BadRequestException("A row needs at least one seat");
        }

        if (perRow > ALPHABET.length()){
            throw new BadRequestException("A row cannot have more than " + ALPHABET.length() + " seats");
        }
    }
}
