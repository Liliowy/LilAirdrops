package me.liliowy.lilairdrops.util;

import org.bukkit.Location;
import org.bukkit.World;

import java.util.Random;

public class LocationHandler {

    private static Random random = new Random();

    public static Location addOffset(Location location, int offset){
        int randomXLocation = random.nextInt(offset + 1) - offset;
        int randomZLocation = random.nextInt(offset + 1) - offset;

        return new Location(location.getWorld(), location.getX() + randomXLocation, location.getY(), location.getZ() + randomZLocation);
    }

    public static Location getRandomLocationInWorld(World world, int maximumBoundX, int minimumBoundX, int maximumBoundY, int maximumBoundZ, int minimumBoundZ){
        int randomXLocation = random.nextInt(maximumBoundX + 1) - minimumBoundX;
        int randomZLocation = random.nextInt(maximumBoundZ + 1) - minimumBoundZ;

        return new Location(world, randomXLocation, maximumBoundY, randomZLocation);
    }
}
