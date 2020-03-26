package com.smartdevicelink.proxy.rpc;

import com.smartdevicelink.proxy.RPCStruct;
import com.smartdevicelink.proxy.rpc.enums.ImageFieldName;
import com.smartdevicelink.proxy.rpc.enums.ImageType;
import com.smartdevicelink.proxy.rpc.enums.MenuLayout;
import com.smartdevicelink.proxy.rpc.enums.TextFieldName;

import java.util.Hashtable;
import java.util.List;

/**
 * @since 6.0
 */
public class WindowCapability extends RPCStruct {
    public static final String KEY_WINDOW_ID = "windowID";
    public static final String KEY_TEXT_FIELDS = "textFields";
    public static final String KEY_IMAGE_FIELDS = "imageFields";
    public static final String KEY_IMAGE_TYPE_SUPPORTED = "imageTypeSupported";
    public static final String KEY_TEMPLATES_AVAILABLE = "templatesAvailable";
    public static final String KEY_NUM_CUSTOM_PRESETS_AVAILABLE = "numCustomPresetsAvailable";
    public static final String KEY_BUTTON_CAPABILITIES = "buttonCapabilities";
    public static final String KEY_SOFT_BUTTON_CAPABILITIES = "softButtonCapabilities";
    public static final String KEY_MENU_LAYOUTS_AVAILABLE = "menuLayoutsAvailable";

    public WindowCapability() {
    }

    public WindowCapability(Hashtable<String, Object> hash) {
        super(hash);
    }

    /**
     * Sets the windowID. The specified ID of the window. Can be set to a predefined window, or omitted for the main window on the main display.
     *
     * @param windowID A unique ID to identify the window. The value of '0' will always be the default main window on the main display and should not be used in this context as it will already be created for the app. See PredefinedWindows enum. Creating a window with an ID that is already in use will be rejected with `INVALID_ID`.
     */
    public void setWindowID(Integer windowID) {
        setValue(KEY_WINDOW_ID, windowID);
    }

    /**
     * Gets the windowID.
     *
     * @return Integer
     */
    public Integer getWindowID() {
        return getInteger(KEY_WINDOW_ID);
    }

    /**
     * Get an array of TextField structures.
     *
     * @return the List of textFields
     */
    @SuppressWarnings("unchecked")
    public List<TextField> getTextFields() {
        return (List<TextField>) getObject(TextField.class, KEY_TEXT_FIELDS);
    }

    /**
     * Set an array of TextField structures. It's set of all fields that support text data.
     * {@code 1<= textFields.size() <= 100}
     *
     * @param textFields the List of textFields
     */
    public void setTextFields(List<TextField> textFields) {
        setValue(KEY_TEXT_FIELDS, textFields);
    }

    /**
     * Get an array of ImageField structures.
     *
     * @return the List of imageFields
     */
    @SuppressWarnings("unchecked")
    public List<ImageField> getImageFields() {
        return (List<ImageField>) getObject(ImageField.class, KEY_IMAGE_FIELDS);
    }

    /**
     * Set an array of ImageField structures. A set of all fields that support images.
     * {@code 1<= ImageFields.size() <= 100}
     *
     * @param imageFields the List of imageFields
     */
    public void setImageFields(List<ImageField> imageFields) {
        setValue(KEY_IMAGE_FIELDS, imageFields);
    }

    /**
     * Get an array of ImageType elements.
     *
     * @return the List of imageTypeSupported
     */
    @SuppressWarnings("unchecked")
    public List<ImageType> getImageTypeSupported() {
        return (List<ImageType>) getObject(ImageType.class, KEY_IMAGE_TYPE_SUPPORTED);
    }

    /**
     * Set an array of ImageType elements.
     * {@code 0<= imageTypeSupported.size() <= 1000}
     *
     * @param imageTypeSupported the List of ImageType
     */
    public void setImageTypeSupported(List<ImageType> imageTypeSupported) {
        setValue(KEY_IMAGE_TYPE_SUPPORTED, imageTypeSupported);
    }

    /**
     * Get an array of templatesAvailable.
     *
     * @return the List of templatesAvailable
     */
    @SuppressWarnings("unchecked")
    public List<String> getTemplatesAvailable() {
        return (List<String>) getObject(String.class, KEY_TEMPLATES_AVAILABLE);
    }

    /**
     * Set an array of templatesAvailable.
     * {@code 0<= templatesAvailable.size() <= 100}
     *
     * @param templatesAvailable the List of String
     */
    public void setTemplatesAvailable(List<String> templatesAvailable) {
        setValue(KEY_TEMPLATES_AVAILABLE, templatesAvailable);
    }

    /**
     * Gets the numCustomPresetsAvailable.
     *
     * @return Integer
     */
    public Integer getNumCustomPresetsAvailable() {
        return getInteger(KEY_NUM_CUSTOM_PRESETS_AVAILABLE);
    }

    /**
     * Sets the numCustomPresetsAvailable. The number of on-window custom presets available (if any); otherwise omitted.
     * {@code 1<= numCustomPresetsAvailable.size() <= 100}
     *
     * @param numCustomPresetsAvailable
     */
    public void setNumCustomPresetsAvailable(Integer numCustomPresetsAvailable) {
        setValue(KEY_NUM_CUSTOM_PRESETS_AVAILABLE, numCustomPresetsAvailable);
    }

    /**
     * Sets the buttonCapabilities portion of the WindowCapability class.
     * {@code 1<= buttonCapabilities.size() <= 100}
     *
     * @param buttonCapabilities It refers to number of buttons and the capabilities of each on-window button.
     */
    public void setButtonCapabilities(List<ButtonCapabilities> buttonCapabilities) {
        setValue(KEY_BUTTON_CAPABILITIES, buttonCapabilities);
    }

    /**
     * Gets the buttonCapabilities portion of the WindowCapability class
     *
     * @return List<ButtonCapabilities>
     * It refers to number of buttons and the capabilities of each on-window button.
     */
    @SuppressWarnings("unchecked")
    public List<ButtonCapabilities> getButtonCapabilities() {
        return (List<ButtonCapabilities>) getObject(ButtonCapabilities.class, KEY_BUTTON_CAPABILITIES);
    }

    /**
     * Sets the softButtonCapabilities portion of the WindowCapability class.
     * {@code 1<= softButtonCapabilities.size() <= 100}
     *
     * @param softButtonCapabilities It refers to number of soft buttons available on-window and the capabilities for each button.
     */
    public void setSoftButtonCapabilities(List<SoftButtonCapabilities> softButtonCapabilities) {
        setValue(KEY_SOFT_BUTTON_CAPABILITIES, softButtonCapabilities);
    }

    /**
     * Gets the softButtonCapabilities portion of the WindowCapability class
     *
     * @return List<SoftButtonCapabilities>
     * It refers to number of soft buttons available on-window and the capabilities for each button.
     */
    @SuppressWarnings("unchecked")
    public List<SoftButtonCapabilities> getSoftButtonCapabilities() {
        return (List<SoftButtonCapabilities>) getObject(SoftButtonCapabilities.class, KEY_SOFT_BUTTON_CAPABILITIES);
    }

    /**
     * An array of available menu layouts. If this parameter is not provided, only the `LIST` layout
     * is assumed to be available
     * @param menuLayout - An array of MenuLayouts
     */
    public void setMenuLayoutsAvailable(List<MenuLayout> menuLayout) {
        setValue(KEY_MENU_LAYOUTS_AVAILABLE, menuLayout);
    }

    /**
     * An array of available menu layouts. If this parameter is not provided, only the `LIST` layout
     * is assumed to be available
     * @return MenuLayout[]
     */
    @SuppressWarnings("unchecked")
    public List<MenuLayout> getMenuLayoutsAvailable() {
        return (List<MenuLayout>) getObject(MenuLayout.class, KEY_MENU_LAYOUTS_AVAILABLE);
    }

    /**
     * Check to see if WindowCapability has an ImageFieldName of a given name.
     * @param name ImageFieldName representing a name of a given Image field that would be stored in WindowCapability
     * @return true if name exist in WindowCapability else false
     */
    public boolean hasImageFieldOfName(ImageFieldName name) {
        if (this.getImageTypeSupported() == null || this.getImageTypeSupported().isEmpty()) {
            return false;
        }
        if (this.getImageFields() != null) {
            for (ImageField field : this.getImageFields()) {
                if (field != null && field.getName() != null && field.getName().equals(name)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Check to see if WindowCapability has a textField of a given name.
     * @param name - TextFieldName representing a name of a given text field that would be stored in WindowCapability
     * @return true if name exist in WindowCapability else false
     */
    public boolean hasTextFieldOfName(TextFieldName name) {
        if (this.getTextFields() != null) {
            for (TextField field : this.getTextFields()) {
                if (field != null && field.getName() != null && field.getName().equals(name)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Method to get number of textFields allowed to be set according to WindowCapability
     * @return linesFound - Number of textFields found in WindowCapability
     */
    public int getMaxNumberOfMainFieldLines() {
        int linesFound = 0;

        List<TextField> textFields = this.getTextFields();
        TextFieldName name;
        if (textFields != null && !textFields.isEmpty()) {
            for (TextField field : textFields) {
                if (field.getName() != null) {
                    name = field.getName();
                    if (name == TextFieldName.mainField1 || name == TextFieldName.mainField2 || name == TextFieldName.mainField3 || name == TextFieldName.mainField4) {
                        linesFound += 1;
                    }
                }
            }
        }

        return linesFound;
    }
}
