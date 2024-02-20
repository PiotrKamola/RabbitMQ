package item;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
public class Item {
    private String itemName;
    private float itemPrice;
    private String priceCurrency;
    private int itemStock;

    public Item(String itemName, float itemPrice, String priceCurrency, int itemStock){
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.priceCurrency = priceCurrency;
        this.itemStock = itemStock;
    }

    public boolean takeItem(int howManyToTake){
        if((this.getItemStock() - howManyToTake) >= 0) {
            setItemStock(this.itemStock - howManyToTake);
            return true;
        }else {
            return false;
        }
    }
}
