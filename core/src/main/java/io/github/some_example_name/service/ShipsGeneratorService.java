package io.github.some_example_name.service;

import java.util.List;

import io.github.some_example_name.battleField.RectCoordinates;
import io.github.some_example_name.ships.Ship;
import io.github.some_example_name.struct.Pair;

public interface ShipsGeneratorService {
    public List<Ship> generateRandomShip(RectCoordinates[][] allRectList);
}
