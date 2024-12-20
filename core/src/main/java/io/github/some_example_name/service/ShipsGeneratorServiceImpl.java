package io.github.some_example_name.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;


import io.github.some_example_name.battleField.RectCoordinates;
import io.github.some_example_name.ships.Rotation;
import io.github.some_example_name.ships.Ship;
import io.github.some_example_name.struct.Pair;

public class ShipsGeneratorServiceImpl implements ShipsGeneratorService{
    private MathService mathService;
    public ShipsGeneratorServiceImpl(MathService mathService) {
        this.mathService = mathService;
    }

    @Override
    public List<Ship> generateRandomShip(RectCoordinates[][] allRectList) {
            try {
                List<Ship> shipListResult = new ArrayList<>();
                shipListResult.addAll(generateShipsWithOnePart(allRectList, 5));
                shipListResult.addAll(generateShipsWithTwoPart(allRectList, 3));
                shipListResult.addAll(generateShipsWithThreePart(allRectList, 2));
                shipListResult.addAll(generateShipsWithFourPart(allRectList, 1));
                return shipListResult;
            }
            catch (Exception e){
                System.out.println(e.getMessage());
            }
            return null;
    }
    private Collection<? extends Ship> generateShipsWithThreePart(RectCoordinates[][] allRectList, int amount) throws Exception {
        List<Ship>shipList=new ArrayList<>();
        Random rand=new Random();
        int iterationCount=0;
        while (shipList.size()<amount){
            List<RectCoordinates> rectCoordinates=new ArrayList<>();
            boolean canDrawShip=false;
            boolean secondPartFound=false;
            boolean thirdPartFound=false;
            Pair firstPart=new Pair();
            Pair secondPart=new Pair();
            Pair thirdPart=new Pair();
            Rotation rotation;
            if(rand.nextBoolean())
                rotation=Rotation.HORIZONTAL;
            else {
                rotation=Rotation.VERTICAL;
            }
            while (!canDrawShip || !secondPartFound  || !thirdPartFound){
                    canDrawShip = false;
                    secondPartFound = false;
                    firstPart.setFirst(rand.nextInt(10));
                    firstPart.setSecond(rand.nextInt(10));
                    Ship ship = new Ship();
                   // ship.setShipsRect(new ArrayList<>(Arrays.asList(new RectCoordinates())));
                    canDrawShip = mathService.canDrawShip(allRectList, firstPart, null);
                    if (canDrawShip) {
                        if (rotation.equals(Rotation.HORIZONTAL)) {
                            if (firstPart.getFirst() + 1 <= 9) {
                                secondPart.setFirst(firstPart.getFirst() + 1);
                                secondPart.setSecond(firstPart.getSecond());
                                secondPartFound = mathService.canDrawShip(allRectList, secondPart, firstPart);
                                if (secondPartFound && firstPart.getFirst() + 2 <= 9) {
                                    thirdPart.setFirst(firstPart.getFirst() + 2);
                                    thirdPart.setSecond(firstPart.getSecond());
                                    thirdPartFound = mathService.canDrawShip(allRectList, thirdPart, secondPart);
                                } else if (secondPartFound && !thirdPartFound && firstPart.getFirst() - 1 > 0) {
                                    thirdPart.setFirst(firstPart.getFirst() - 1);
                                    thirdPart.setSecond(firstPart.getSecond());
                                    thirdPartFound = mathService.canDrawShip( allRectList, thirdPart, firstPart);
                                }
                            } else if (firstPart.getFirst() - 1 > 0) {
                                secondPart.setFirst(firstPart.getFirst() - 1);
                                secondPart.setSecond(firstPart.getSecond());
                                secondPartFound = mathService.canDrawShip(allRectList, secondPart, firstPart);
                                if (secondPartFound && firstPart.getFirst() - 2 > 0) {
                                    thirdPart.setFirst(firstPart.getFirst() - 2);
                                    thirdPart.setSecond(firstPart.getSecond());
                                    thirdPartFound = mathService.canDrawShip(allRectList, thirdPart, secondPart);
                                } else if (secondPartFound && !thirdPartFound && firstPart.getFirst() + 1 <= 9) {
                                    thirdPart.setFirst(firstPart.getFirst() + 1);
                                    thirdPart.setSecond(firstPart.getSecond());
                                    thirdPartFound = mathService.canDrawShip( allRectList, thirdPart, firstPart);
                                }
                            }
                        }
                        else {
                            if (firstPart.getSecond() + 1 <= 9) {
                                secondPart.setFirst(firstPart.getFirst());
                                secondPart.setSecond(firstPart.getSecond()+ 1);
                                secondPartFound = mathService.canDrawShip( allRectList, secondPart, firstPart);
                                if (secondPartFound && firstPart.getSecond() + 2 <= 9) {
                                    thirdPart.setFirst(firstPart.getFirst());
                                    thirdPart.setSecond(firstPart.getSecond()+2);
                                    thirdPartFound = mathService.canDrawShip(allRectList, thirdPart, secondPart);
                                } else if (secondPartFound && !thirdPartFound && firstPart.getSecond() - 1 > 0) {
                                    thirdPart.setFirst(firstPart.getFirst());
                                    thirdPart.setSecond(firstPart.getSecond()-1);
                                    thirdPartFound = mathService.canDrawShip(allRectList, thirdPart, firstPart);
                                }
                            } else if (firstPart.getSecond() - 1 > 0) {
                                secondPart.setFirst(firstPart.getFirst());
                                secondPart.setSecond(firstPart.getSecond()-1);
                                secondPartFound = mathService.canDrawShip(allRectList, secondPart, firstPart);
                                if (secondPartFound && firstPart.getSecond() - 2 > 0) {
                                    thirdPart.setFirst(firstPart.getFirst());
                                    thirdPart.setSecond(firstPart.getSecond()-2);
                                    thirdPartFound = mathService.canDrawShip(allRectList, thirdPart, secondPart);
                                } else if (secondPartFound && !thirdPartFound && firstPart.getSecond() + 1 <= 9) {
                                    thirdPart.setFirst(firstPart.getFirst());
                                    thirdPart.setSecond(firstPart.getSecond()+1);
                                    thirdPartFound = mathService.canDrawShip(allRectList, thirdPart, firstPart);
                                }
                            }
                        }
                    }
                    if (canDrawShip && secondPartFound && thirdPartFound) {
                        RectCoordinates firstPartRectCoordinates = new RectCoordinates();
                        firstPartRectCoordinates.setUse(true);
                        firstPartRectCoordinates.setxCoordinate(firstPart.getFirst());
                        firstPartRectCoordinates.setyCoordinate(firstPart.getSecond());
                        RectCoordinates secondPartRectCoordinates = new RectCoordinates();
                        secondPartRectCoordinates.setUse(true);
                        secondPartRectCoordinates.setxCoordinate(secondPart.getFirst());
                        secondPartRectCoordinates.setyCoordinate(secondPart.getSecond());
                        RectCoordinates thirdPartRectCoordinates = new RectCoordinates();
                        thirdPartRectCoordinates.setUse(true);
                        thirdPartRectCoordinates.setxCoordinate(thirdPart.getFirst());
                        thirdPartRectCoordinates.setyCoordinate(thirdPart.getSecond());
                        rectCoordinates.add(thirdPartRectCoordinates);
                        rectCoordinates.add(firstPartRectCoordinates);
                        rectCoordinates.add(secondPartRectCoordinates);
                        Ship newShip = new Ship();
                        newShip.setShipsRect(rectCoordinates);
                        newShip.setRotation(rotation);
                        shipList.add(newShip);
                    }
                    iterationCount++;
                if(iterationCount==100)
                    throw new Exception("Cycle error");
            }
            allRectList[firstPart.getFirst()][firstPart.getSecond()].setUse(true);
            allRectList[secondPart.getFirst()][secondPart.getSecond()].setUse(true);
            allRectList[thirdPart.getFirst()][thirdPart.getSecond()].setUse(true);
            for(RectCoordinates currentRectCoordinates:rectCoordinates) {
                allRectList[currentRectCoordinates.getxCoordinate()][currentRectCoordinates.getyCoordinate()].setUse(true);
            }
        }
        return shipList;
    }

    private Collection<? extends Ship> generateShipsWithFourPart(RectCoordinates[][] allRectList, int amount) throws Exception {
        List<Ship>shipList=new ArrayList<>();
        Random rand=new Random();
        int iterationCount=0;
        while (shipList.size()<amount){
            List<RectCoordinates> rectCoordinates=new ArrayList<>();
            boolean canDrawShip=false;
            boolean secondPartFound=false;
            boolean thirdPartFound=false;
            boolean fourPartFound=false;
            Pair firstPart=new Pair();
            Pair secondPart=new Pair();
            Pair thirdPart=new Pair();
            Pair foursPart=new Pair();
            Rotation rotation;
            if(rand.nextBoolean())
                rotation=Rotation.HORIZONTAL;
            else {
                rotation=Rotation.VERTICAL;
            }
            while (!canDrawShip || !secondPartFound  || !thirdPartFound || !fourPartFound){
                    iterationCount++;
                    canDrawShip = false;
                    secondPartFound = false;
                    firstPart.setFirst(rand.nextInt(10));
                    firstPart.setSecond(rand.nextInt(10));
                    Ship ship = new Ship();
                    //ship.setShipsRect(new ArrayList<>(Arrays.asList(new RectCoordinates())));
                    canDrawShip = mathService.canDrawShip( allRectList, firstPart, null);
                    if (canDrawShip) {
                        if (rotation.equals(Rotation.VERTICAL)) {
                            if (firstPart.getFirst() + 1 <= 9) {
                                secondPart.setFirst(firstPart.getFirst() + 1);
                                secondPart.setSecond(firstPart.getSecond());
                                secondPartFound = mathService.canDrawShip( allRectList, secondPart, firstPart);
                                if (secondPartFound && firstPart.getFirst() + 2 <= 9) {
                                    thirdPart.setFirst(firstPart.getFirst() + 2);
                                    thirdPart.setSecond(firstPart.getSecond());
                                    thirdPartFound = mathService.canDrawShip(allRectList, thirdPart, secondPart);
                                }
                                if (secondPartFound && thirdPartFound && firstPart.getFirst() + 3 <=9) {
                                    foursPart.setFirst(firstPart.getFirst() + 3);
                                    foursPart.setSecond(firstPart.getSecond());
                                    fourPartFound = mathService.canDrawShip(allRectList, foursPart, thirdPart);
                                }
                            }
                            if ((!secondPartFound || !thirdPartFound || !fourPartFound) && firstPart.getFirst() - 1 > 0) {
                                secondPart.setFirst(firstPart.getFirst() - 1);
                                secondPart.setSecond(firstPart.getSecond());
                                secondPartFound = mathService.canDrawShip(allRectList, secondPart, firstPart);
                                if (secondPartFound && firstPart.getFirst() - 2 > 0) {
                                    thirdPart.setFirst(firstPart.getFirst() - 2);
                                    thirdPart.setSecond(firstPart.getSecond());
                                    thirdPartFound = mathService.canDrawShip(allRectList, thirdPart, secondPart);
                                }
                                if (secondPartFound && thirdPartFound && firstPart.getFirst() - 3 > 0) {
                                    foursPart.setFirst(firstPart.getFirst() - 3);
                                    foursPart.setSecond(firstPart.getSecond());
                                    fourPartFound = mathService.canDrawShip( allRectList, foursPart, thirdPart);
                                }
                            }
                        }
                        else {
                            if (firstPart.getSecond() + 1 <= 9) {
                                secondPart.setFirst(firstPart.getFirst());
                                secondPart.setSecond(firstPart.getSecond()+1);
                                secondPartFound = mathService.canDrawShip( allRectList, secondPart, firstPart);
                                if (secondPartFound && firstPart.getSecond() + 2 <= 9) {
                                    thirdPart.setFirst(firstPart.getFirst());
                                    thirdPart.setSecond(firstPart.getSecond()+2);
                                    thirdPartFound = mathService.canDrawShip(allRectList, thirdPart, secondPart);
                                }  if (secondPartFound && thirdPartFound && firstPart.getSecond() + 3 <=9) {
                                    foursPart.setFirst(firstPart.getFirst());
                                    foursPart.setSecond(firstPart.getSecond()+3);
                                    fourPartFound = mathService.canDrawShip( allRectList, foursPart, thirdPart);
                                }
                            }
                            if ((!secondPartFound || !thirdPartFound || !fourPartFound) && firstPart.getSecond() - 1 > 0) {
                                secondPart.setFirst(firstPart.getFirst());
                                secondPart.setSecond(firstPart.getSecond()-1);
                                secondPartFound = mathService.canDrawShip( allRectList, secondPart, firstPart);
                                if (secondPartFound && firstPart.getSecond() - 2 > 0) {
                                    thirdPart.setFirst(firstPart.getFirst());
                                    thirdPart.setSecond(firstPart.getSecond()-2);
                                    thirdPartFound = mathService.canDrawShip(allRectList, thirdPart, secondPart);
                                }  if (secondPartFound && thirdPartFound && firstPart.getSecond() - 3 > 0) {
                                    foursPart.setFirst(firstPart.getFirst());
                                    foursPart.setSecond(firstPart.getSecond()-3);
                                    fourPartFound = mathService.canDrawShip( allRectList, foursPart, thirdPart);
                                }
                            }
                        }
                    }
                    if (canDrawShip && secondPartFound && thirdPartFound && fourPartFound) {
                        RectCoordinates firstPartRectCoordinates = new RectCoordinates();
                        firstPartRectCoordinates.setUse(true);
                        firstPartRectCoordinates.setxCoordinate(firstPart.getFirst());
                        firstPartRectCoordinates.setyCoordinate(firstPart.getSecond());
                        RectCoordinates secondPartRectCoordinates = new RectCoordinates();
                        secondPartRectCoordinates.setUse(true);
                        secondPartRectCoordinates.setxCoordinate(secondPart.getFirst());
                        secondPartRectCoordinates.setyCoordinate(secondPart.getSecond());
                        RectCoordinates thirdPartRectCoordinates = new RectCoordinates();
                        thirdPartRectCoordinates.setUse(true);
                        thirdPartRectCoordinates.setxCoordinate(thirdPart.getFirst());
                        thirdPartRectCoordinates.setyCoordinate(thirdPart.getSecond());
                        RectCoordinates foursPartRectCoordinates = new RectCoordinates();
                        foursPartRectCoordinates.setUse(true);
                        foursPartRectCoordinates.setxCoordinate(foursPart.getFirst());
                        foursPartRectCoordinates.setyCoordinate(foursPart.getSecond());
                        rectCoordinates.add(foursPartRectCoordinates);
                        rectCoordinates.add(thirdPartRectCoordinates);
                        rectCoordinates.add(firstPartRectCoordinates);
                        rectCoordinates.add(secondPartRectCoordinates);
                        Ship newShip = new Ship();
                        newShip.setShipsRect(rectCoordinates);
                        newShip.setRotation(rotation);
                        shipList.add(newShip);
                    }
                    if(iterationCount==100)
                        throw new Exception("Cycle error");
            }
            allRectList[firstPart.getFirst()][firstPart.getSecond()].setUse(true);
            allRectList[secondPart.getFirst()][secondPart.getSecond()].setUse(true);
            allRectList[thirdPart.getFirst()][thirdPart.getSecond()].setUse(true);
            for(RectCoordinates currentRectCoordinates:rectCoordinates) {
                allRectList[currentRectCoordinates.getxCoordinate()][currentRectCoordinates.getyCoordinate()].setUse(true);
            }
        }
        return shipList;
    }

    private Collection<? extends Ship> generateShipsWithTwoPart(RectCoordinates[][] allRectList,int amount) throws Exception {
        List<Ship>shipList=new ArrayList<>();
        Random rand=new Random();
        int iterationCount=0;
        while (shipList.size()<amount){
            List<RectCoordinates> rectCoordinates=new ArrayList<>();
            boolean canDrawShip=false;
            boolean secondPartFound=false;
            Pair firstPair=new Pair();
            Pair secondPart=new Pair();
            Rotation rotation;
            if(rand.nextBoolean())
                rotation=Rotation.HORIZONTAL;
            else {
                rotation=Rotation.VERTICAL;
            }
            while (!canDrawShip || !secondPartFound){
                canDrawShip=false;
                secondPartFound=false;
                firstPair.setFirst(rand.nextInt(10));
                firstPair.setSecond(rand.nextInt(10));
                Ship ship=new Ship();
                //ship.setShipsRect(new ArrayList<>(Arrays.asList(new RectCoordinates())));
                canDrawShip=mathService.canDrawShip(allRectList,firstPair,null);
                if(canDrawShip){
                    if(rotation.equals(Rotation.VERTICAL)){
                        if(firstPair.getFirst()+1<=9){
                            secondPart.setFirst(firstPair.getFirst()+1);
                            secondPart.setSecond(firstPair.getSecond());
                            secondPartFound=mathService.canDrawShip(allRectList,secondPart,firstPair);
                        }
                        if(!secondPartFound && firstPair.getFirst()-1>0){
                            secondPart.setFirst(firstPair.getFirst()-1);
                            secondPart.setSecond(firstPair.getSecond());
                            secondPartFound=mathService.canDrawShip(allRectList,secondPart,firstPair);
                        }
                    }
                    else{
                        if(firstPair.getSecond()+1<=9){
                            secondPart.setFirst(firstPair.getFirst());
                            secondPart.setSecond(firstPair.getSecond()+1);
                            secondPartFound=mathService.canDrawShip(allRectList,secondPart,firstPair);
                        }
                        if(!secondPartFound && firstPair.getSecond()-1>0){
                            secondPart.setFirst(firstPair.getFirst());
                            secondPart.setSecond(firstPair.getSecond()-1);
                            secondPartFound=mathService.canDrawShip(allRectList,secondPart,firstPair);
                        }
                    }
                }
                if(canDrawShip && secondPartFound) {
                    RectCoordinates firstPartRectCoordinates = new RectCoordinates();
                    firstPartRectCoordinates.setUse(true);
                    firstPartRectCoordinates.setxCoordinate(firstPair.getFirst());
                    firstPartRectCoordinates.setyCoordinate(firstPair.getSecond());
                    RectCoordinates secondPartRectCoordinates = new RectCoordinates();
                    secondPartRectCoordinates.setUse(true);
                    secondPartRectCoordinates.setxCoordinate(secondPart.getFirst());
                    secondPartRectCoordinates.setyCoordinate(secondPart.getSecond());
                    rectCoordinates.add(firstPartRectCoordinates);
                    rectCoordinates.add(secondPartRectCoordinates);
                    Ship newShip=new Ship();
                    newShip.setShipsRect(rectCoordinates);
                    newShip.setRotation(rotation);
                    shipList.add(newShip);
                }
                iterationCount++;
                if(iterationCount==500)
                    throw new Exception("Cycle error");
            }
            allRectList[firstPair.getFirst()][firstPair.getSecond()].setUse(true);
            allRectList[secondPart.getFirst()][secondPart.getSecond()].setUse(true);
            for(RectCoordinates currentRectCoordinates:rectCoordinates) {
                allRectList[currentRectCoordinates.getxCoordinate()][currentRectCoordinates.getyCoordinate()].setUse(true);
            }
        }
        return shipList;
    }

    private Collection<? extends Ship> generateShipsWithOnePart(RectCoordinates[][] allRectList, int amount) throws Exception {
        List<Ship>shipList=new ArrayList<>();
        Random rand = new Random();
        Set<RectCoordinates> setRectCoordinates=new HashSet<>();
        int iterationCount=0;
        while (setRectCoordinates.size()<amount){
            RectCoordinates rectCoordinates=new RectCoordinates();
            boolean canDrawSHip=false;
            while (!canDrawSHip){
                Pair pair=new Pair();
                pair.setFirst(rand.nextInt(10));
                pair.setSecond(rand.nextInt(10));
                Ship ship=new Ship();
                //ship.setShipsRect(new ArrayList<>());
                canDrawSHip=mathService.canDrawShip(allRectList,pair,null);
                rectCoordinates.setxCoordinate(pair.getFirst());
                rectCoordinates.setyCoordinate(pair.getSecond());
                iterationCount++;
                if(iterationCount==100)
                    throw new Exception("Cycle error");
            }
            setRectCoordinates.add(rectCoordinates);
            allRectList[rectCoordinates.getxCoordinate()][rectCoordinates.getyCoordinate()].setUse(true);
        }

        for(RectCoordinates rectCoordinates:setRectCoordinates){
            Ship ship=new Ship();
            ship.setShipsRect(Arrays.asList(rectCoordinates));
            shipList.add(ship);
        }
        return shipList;
    }
}
