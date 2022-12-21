package sig.model;

public class LineModel {

    private String _ItemName;
    private double _ItemPrice;
    private int _Count;
    private HeaderModel _Header;

    public LineModel() {
    }

    public LineModel(String item, double price, int count, HeaderModel _Header) {
        this._ItemName = item;
        this._ItemPrice = price;
        this._Count = count;
        this._Header = _Header;
    }

    public HeaderModel getHeader() {
        return _Header;
    }

    public String getItem() {
        return _ItemName;
    }

    public double getPrice() {
        return _ItemPrice;
    }

    public int getCount() {
        return _Count;
    }

    public double getLineTotal() {
        return _ItemPrice * _Count;
    }

}
