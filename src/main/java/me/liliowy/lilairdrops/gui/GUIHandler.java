package me.liliowy.lilairdrops.gui;

import me.liliowy.lilairdrops.gui.inventories.AirdropChancesInventory;
import me.liliowy.lilairdrops.gui.inventories.MainInventory;
import me.liliowy.lilairdrops.gui.inventories.RandomAirdropInventory;
import me.liliowy.lilairdrops.objects.Airdrop;

public class GUIHandler {

    private MainInventory mainInventory;
    private RandomAirdropInventory randomAirdropInventory;
    private AirdropChancesInventory airdropChancesInventory;

    public GUIHandler(){
        mainInventory = new MainInventory();
        randomAirdropInventory = new RandomAirdropInventory();
        airdropChancesInventory = new AirdropChancesInventory();
    }

    public MainInventory getMainInventory(){
        return mainInventory;
    }
    public RandomAirdropInventory getRandomAirdropInventory(){
        return randomAirdropInventory;
    }
    public AirdropChancesInventory getAirdropChancesInventory(){
        return airdropChancesInventory;
    }
}
