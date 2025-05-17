interface UIComponentFactory {
    Button createButton();

    InputField createInputField();
}

class WebFactory implements UIComponentFactory {
    public Button createButton() {
        return new WebButton();
    }

    public InputField createInputField() {
        return new WebInput();
    }
}
