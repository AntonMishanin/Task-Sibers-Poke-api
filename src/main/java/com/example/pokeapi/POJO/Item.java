package com.example.pokeapi.POJO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Item {

  //  @SerializedName("firmness")
   // @Expose
   /// private Firmness firmness;
  //  @SerializedName("flavors")
  ///  @Expose
   // private List<Flavor> flavors = null;
    @SerializedName("growth_time")
    @Expose
    private Integer growthTime;
    @SerializedName("id")
    @Expose
    private Integer id;
   // @SerializedName("item")
   // @Expose
    //private Item_ item;
    @SerializedName("max_harvest")
    @Expose
    private Integer maxHarvest;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("natural_gift_power")
    @Expose
    private Integer naturalGiftPower;
   // @SerializedName("natural_gift_type")
  //  @Expose
  //  private NaturalGiftType naturalGiftType;
    @SerializedName("size")
    @Expose
    private Integer size;
    @SerializedName("smoothness")
    @Expose
    private Integer smoothness;
    @SerializedName("soil_dryness")
    @Expose
    private Integer soilDryness;

  //  public Firmness getFirmness() {
  //      return firmness;
   // }

    //public void setFirmness(Firmness firmness) {
   //     this.firmness = firmness;
   // }

   // public List<Flavor> getFlavors() {
   //     return flavors;
   // }

   // public void setFlavors(List<Flavor> flavors) {
   //     this.flavors = flavors;
   // }

    public Integer getGrowthTime() {
        return growthTime;
    }

    public void setGrowthTime(Integer growthTime) {
        this.growthTime = growthTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

   // public Item_ getItem() {
   //     return item;
   // }

   // public void setItem(Item_ item) {
   //     this.item = item;
   // }

    public Integer getMaxHarvest() {
        return maxHarvest;
    }

    public void setMaxHarvest(Integer maxHarvest) {
        this.maxHarvest = maxHarvest;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getNaturalGiftPower() {
        return naturalGiftPower;
    }

    public void setNaturalGiftPower(Integer naturalGiftPower) {
        this.naturalGiftPower = naturalGiftPower;
    }

 //   public NaturalGiftType getNaturalGiftType() {
  //      return naturalGiftType;
  //  }

  //  public void setNaturalGiftType(NaturalGiftType naturalGiftType) {
  //      this.naturalGiftType = naturalGiftType;
  //  }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Integer getSmoothness() {
        return smoothness;
    }

    public void setSmoothness(Integer smoothness) {
        this.smoothness = smoothness;
    }

    public Integer getSoilDryness() {
        return soilDryness;
    }

    public void setSoilDryness(Integer soilDryness) {
        this.soilDryness = soilDryness;
    }

}
