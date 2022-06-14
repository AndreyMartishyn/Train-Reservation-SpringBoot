package ua.martishyn.app.repository;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ua.martishyn.app.entities.RoutePoint;
import ua.martishyn.app.repositories.RouteRepository;

import java.util.List;
import java.util.stream.Collectors;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RouteTest {
    @Autowired
    private RouteRepository routeRepository;

    @Test
    public void test(){
//        final List<RoutePoint> all = routeRepository.findAll();
//        Assert.assertEquals(10, all.size());
//        final RoutePoint routePoint = all.get(0);
//        System.out.println(routePoint.getId());
//        System.out.println(routePoint.getId().getStation());
//        System.out.println(routePoint.getArrival());
//        System.out.println(routePoint.getDeparture());
//        System.out.println(routePoint.getTrain());
//


    }
}
