package ingredient;

import java.util.List;

public class ResponseData {
    private boolean success;
    private List<IngredientData> data;

    public ResponseData() {}

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<IngredientData> getData() {
        return data;
    }

    public void setData(List<IngredientData> data) {
        this.data = data;
    }
}
