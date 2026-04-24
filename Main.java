import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.geometry.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import java.time.LocalDate;
import java.util.List;

public class Main extends Application {

    private String userName = "User";
    private ERSystem system;
    private BorderPane root;

    private static final String GREEN_DARK = "#4A6741";
    private static final String GREEN_MID = "#6B8F3E";
    private static final String GREEN_LIGHT = "#C8E6A0";
    private static final String GREEN_BG = "#EEF5E0";
    private static final String WHITE = "#FFFFFF";
    private static final String GRAY = "#888888";
    private static final String PANEL = "#F8FCEF";

    @Override
    public void start(Stage stage) {
        system = new ERSystem();
        userName = askForName();

        this.root = new BorderPane();
        Button[] navBtns = new Button[5];
        root.setLeft(buildSidebar(root, navBtns));
        root.setCenter(buildDashboard());
        root.setStyle("-fx-background-color: " + GREEN_DARK + ";");

        Scene scene = new Scene(root, 800, 600);
        stage.setTitle("HospitalER Management");
        stage.setScene(scene);
        stage.setResizable(true);
        stage.setMinWidth(400);
        stage.setMinHeight(300);
        stage.show();
    }

    private String askForName() {
        TextInputDialog dlg = new TextInputDialog("");
        dlg.setTitle("Welcome to HospitalER");
        dlg.setHeaderText("Hospital ER Management System");
        dlg.setContentText("Enter your name:");
        dlg.getDialogPane().setStyle("-fx-background-color: " + GREEN_BG + ";");
        return dlg.showAndWait().orElse("User");
    }

    private VBox buildSidebar(BorderPane root, Button[] navBtns) {
        VBox sidebar = new VBox(18);
        sidebar.setPadding(new Insets(28, 20, 28, 20));
        sidebar.setPrefWidth(250);
        
        sidebar.setStyle("-fx-background-color: " + WHITE + "; -fx-background-radius: 30;");
        BorderPane.setMargin(sidebar, new Insets(18, 10, 18, 18));

        HBox logo = new HBox(10);
        logo.setAlignment(Pos.CENTER_LEFT);
        logo.setPadding(new Insets(4, 0, 22, 0));

        Label icon = new Label("❤");
        icon.setStyle("-fx-font-size: 22px; -fx-text-fill: #D93B3B;");

        VBox logoText = new VBox(2);
        Label logoTitle = new Label("HospitalER");
        logoTitle.setStyle("-fx-font-size: 26px; -fx-font-weight: bold; -fx-text-fill: #111111;");
        
        Label logoSub = new Label("Emergency\nManagement");
        logoSub.setStyle("-fx-font-size: 15px; -fx-text-fill: #111111;");
        logoText.getChildren().addAll(logoTitle, logoSub);

        logo.getChildren().addAll(icon, logoText);

        navBtns[0] = navBtn("⌂", "Dashboard", true);
        navBtns[1] = navBtn("⚠", "Queue", false);
        navBtns[2] = navBtn("☷", "Patient Registry", false);
        navBtns[3] = navBtn("☌", "Registration", false);
        navBtns[4] = navBtn("▥", "Statistics", false);

        navBtns[0].setOnAction(e -> { root.setCenter(buildDashboard()); setActive(navBtns, 0); });
        navBtns[1].setOnAction(e -> { root.setCenter(buildQueuePage()); setActive(navBtns, 1); });
        navBtns[2].setOnAction(e -> { root.setCenter(buildRegistryPage()); setActive(navBtns, 2); });
        navBtns[3].setOnAction(e -> { root.setCenter(buildRegistrationPage()); setActive(navBtns, 3); });
        navBtns[4].setOnAction(e -> { root.setCenter(buildStatisticsPage()); setActive(navBtns, 4); });

        sidebar.getChildren().add(logo);
        sidebar.getChildren().addAll(navBtns);
        return sidebar;
    }

    private Button navBtn(String icon, String label, boolean active) {
        Label iconLabel = new Label(icon);
        iconLabel.setStyle("-fx-font-size: 20px;");

        Label textLabel = new Label(label);
        textLabel.setStyle("-fx-font-size: 17px; -fx-font-weight: bold;");

        HBox content = new HBox(10, iconLabel, textLabel);
        content.setAlignment(Pos.CENTER_LEFT);
        
        Button btn = new Button();
        btn.setGraphic(content);



        btn.setPrefWidth(230);
        btn.setMinHeight(45);
        btn.setAlignment(Pos.CENTER_LEFT);
        btn.setWrapText(true);
        btn.setStyle(active ? activeNav() : inactiveNav());

        btn.setOnMouseEntered(e -> {
            if (!btn.getStyle().contains(GREEN_LIGHT)) btn.setStyle(hoverNav());
        });
        btn.setOnMouseExited(e -> {
            if (!btn.getStyle().contains(GREEN_LIGHT)) btn.setStyle(inactiveNav());
        });
        return btn;
    }

    private void setActive(Button[] btns, int idx) {
        for (int i = 0; i < btns.length; i++) {
            btns[i].setStyle(i == idx ? activeNav() : inactiveNav());
        }
    }

    private String activeNav() {
        return "-fx-background-color: " + GREEN_LIGHT + ";"
                + "-fx-text-fill: " + GREEN_DARK + ";"
                + "-fx-font-size: 17px;"
                + "-fx-font-weight: bold;"
                + "-fx-padding: 16 18 16 18;"
                + "-fx-background-radius: 18;"
                + "-fx-cursor: hand;";
    }

    private String inactiveNav() {
        return "-fx-background-color: transparent;"
                + "-fx-text-fill: " + GREEN_DARK + ";"
                + "-fx-font-size: 17px;"
                + "-fx-font-weight: bold;"
                + "-fx-padding: 16 18 16 18;"
                + "-fx-background-radius: 18;"
                + "-fx-cursor: hand;";
    }

    private String hoverNav() {
        return "-fx-background-color: #E8F5D0;"
                + "-fx-text-fill: " + GREEN_DARK + ";"
                + "-fx-font-size: 17px;"
                + "-fx-font-weight: bold;"
                + "-fx-padding: 16 18 16 18;"
                + "-fx-background-radius: 18;"
                + "-fx-cursor: hand;";
    }

    private ScrollPane shell(VBox content) {
        VBox outer = new VBox();
        outer.setPadding(new Insets(18));
        outer.setStyle("-fx-background-color: " + GREEN_BG + "; -fx-background-radius: 34;");

        HBox center = new HBox(content);
        center.setAlignment(Pos.TOP_CENTER);
        outer.getChildren().add(center);

        ScrollPane sp = new ScrollPane(outer);
        sp.setFitToWidth(true);
        sp.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        sp.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        sp.setStyle("-fx-background-color: transparent; -fx-background: transparent; -fx-border-color: transparent;");
        return sp;
    }

    private VBox pageHeader(String title) {
        HBox top = new HBox();
        top.setAlignment(Pos.CENTER_LEFT);

        Label titleLbl = new Label(title);
        titleLbl.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: " + GREEN_DARK + ";");

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        top.getChildren().addAll(titleLbl, spacer);

        VBox box = new VBox(12);
        box.getChildren().add(top);
        return box;
    }

    private VBox whitePanel() {
        VBox panel = new VBox();
        panel.setPadding(new Insets(20));
        panel.setStyle("-fx-background-color: " + WHITE + "; -fx-background-radius: 25; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 15, 0, 0, 4);");
        panel.setMinWidth(800);
        return panel;
    }

    private Label label(String text, int size, String color, boolean bold) {
        Label l = new Label(text);
        l.setWrapText(true);
        l.setStyle("-fx-font-size: " + size + "px; -fx-text-fill: " + color + ";"
                + (bold ? "-fx-font-weight: bold;" : ""));
        return l;
    }

    private TextField field(String prompt) {
        TextField f = new TextField();
        f.setPromptText(prompt);
        f.setPrefHeight(36);
        f.setMaxWidth(400);
        f.setStyle("-fx-background-color: " + PANEL + ";"
                + "-fx-border-color: #D3D7A6;"
                + "-fx-border-width: 1;"
                + "-fx-border-radius: 8;"
                + "-fx-background-radius: 8;"
                + "-fx-padding: 10 14 10 14;"
                + "-fx-font-size: 15px;");
        return f;
    }

    private ComboBox<String> combo(String prompt, String... items) {
        ComboBox<String> c = new ComboBox<>();
        c.getItems().addAll(items);
        c.setPromptText(prompt);
        c.setPrefHeight(36);
        c.setMaxWidth(400);
        c.setStyle("-fx-background-color: " + PANEL + ";"
                + "-fx-border-color: #D3D7A6;"
                + "-fx-border-width: 1;"
                + "-fx-border-radius: 8;"
                + "-fx-background-radius: 8;"
                + "-fx-font-size: 15px;");
        return c;
    }

    private Button actionButton(String text) {
        Button b = new Button(text);
        b.setPrefHeight(50);
        b.setStyle("-fx-background-color: " + GREEN_LIGHT + ";"
                + "-fx-text-fill: " + GREEN_DARK + ";"
                + "-fx-font-size: 20px;"
                + "-fx-font-weight: bold;"
                + "-fx-background-radius: 18;"
                + "-fx-cursor: hand;");
        return b;
    }

    private ScrollPane buildQueuePage() {
        VBox page = new VBox(34);
        page.setPadding(new Insets(34, 28, 34, 28));

        VBox panel = whitePanel();

        VBox header = pageHeader("TRIAGE QUEUE");

        VBox listWrap = new VBox();
        listWrap.setPadding(new Insets(30, 70, 30, 70));

        List<Patient> patients = system.getTriage().getAllPatientsSorted();
        if (patients.isEmpty()) {
            Label empty = label("No patients currently in queue.", 16, GRAY, false);
            listWrap.getChildren().add(empty);
        } else {
            int pos = 1;
            for (Patient p : patients) {
                listWrap.getChildren().add(queueRow(pos++, p));
            }
        }

        panel.getChildren().addAll(header, listWrap);
        page.getChildren().add(panel);
        return shell(page);
    }

    private HBox queueRow(int pos, Patient p) {
        HBox row = new HBox(18);
        row.setAlignment(Pos.CENTER_LEFT);
        row.setPadding(new Insets(18, 26, 18, 26));
        row.setStyle("-fx-background-color: " + WHITE + ";"
                + "-fx-background-radius: 20;"
                + "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.10), 14, 0, 0, 4);");

        Label posLbl = new Label(String.valueOf(pos));
        posLbl.setMinWidth(28);
        posLbl.setStyle("-fx-font-size: 15px; -fx-font-weight: bold; -fx-text-fill: #666666;");

        Label avatar = new Label("🧑");
        avatar.setStyle("-fx-font-size: 28px;");

        VBox info = new VBox(3);
        info.getChildren().addAll(
                label(p.getName() + ", " + p.getAge() + p.getGender(), 22, GREEN_DARK, true),
                label(p.getCondition(), 18, "#333333", false),
                label(p.getArrivalTime().toString(), 14, GRAY, false)
        );
        HBox.setHgrow(info, Priority.ALWAYS);

        Label badge = new Label(p.getSeverityLabel().toUpperCase());
        badge.setStyle("-fx-background-color: " + p.getSeverityColor() + ";"
                + "-fx-text-fill: white;"
                + "-fx-font-size: 17px;"
                + "-fx-font-weight: bold;"
                + "-fx-padding: 14 22 14 22;"
                + "-fx-background-radius: 999;");

        row.getChildren().addAll(posLbl, avatar, info, badge);
        return row;
    }

    private ScrollPane buildRegistrationPage() {
        VBox page = new VBox(20);
        page.setPadding(new Insets(30, 25, 30, 25));
        page.setStyle("-fx-background-color: " + GREEN_BG + ";");

        VBox panel = whitePanel();
        VBox header = pageHeader("REGISTRATION");


        HBox body = new HBox(20);
        body.setAlignment(Pos.TOP_LEFT);

        VBox form = new VBox(15);
        form.setPadding(new Insets(10, 0, 10, 0));
        form.setMaxWidth(Double.MAX_VALUE); //let the form stretch
        form.setAlignment(Pos.TOP_LEFT);
        HBox.setHgrow(form, Priority.ALWAYS);


        Label section = label("Patient Details", 18, GREEN_DARK, true);

        TextField nameField = field("Full name");
        TextField ageField = field("Age");
        ComboBox<String> gender = combo("Gender", "Male", "Female"); //combo is for dropdown
        TextField reportField = field("Report");
        ComboBox<String> severity = combo("Severity", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10");
        ComboBox<String> bloodType = combo("Blood type", "A+", "A-", "B+", "B-", "O+", "O-", "AB+", "AB-");
        TextField allergyField = field("Allergies");
        TextField contactField = field("Mobile Number");
        ComboBox<String> insuranceField = combo("Insurance Provider", "Daman", "MetLife");


        form.getChildren().addAll(
                section,
                nameField,
                ageField,
                gender,
                reportField,
                severity,
                bloodType,
                allergyField,
                contactField,
                insuranceField
        );

        Button register = new Button("Register Patient");
        register.setPrefHeight(46);
        register.setMaxWidth(260);

        register.setStyle(
            "-fx-background-color:" + GREEN_DARK + ";" +
            "-fx-text-fill:white;" +
            "-fx-font-size:14px;" +
            "-fx-font-weight:bold;" +
            "-fx-padding:10 20 10 20;" +
            "-fx-background-radius:12;" +
            "-fx-cursor:hand;"
        );

        register.setOnAction(e -> {
            try {
                String name = nameField.getText().trim();
                String ageStr = ageField.getText().trim();
                String g = gender.getValue();
                String report = reportField.getText().trim();
                String sevVal = severity.getValue();
                String bt = bloodType.getValue();
                String a = allergyField.getText().trim();
                String contact = contactField.getText().trim();
                String insurance = insuranceField.getValue();


                if (name.length() < 2) {
                    throw new ERException("The name you entered is too short !");
                }

                if (name.isEmpty()||ageStr.isEmpty() ||g==null||report.isEmpty()||sevVal == null||bt == null||a==null||contact.isEmpty()||insurance == null) {
                    throw new ERException("Please fill all the fields !");
                }

                int age = Integer.parseInt(ageStr);
                if (age < 0||age > 120) {
                    throw new ERException("Please only enter valid age!");
                }
                
                int sev = Integer.parseInt(sevVal);

                Patient p = system.registerPatient(name, age, g.substring(0, 1), report, sev, bt, a, contact, insurance);
                system.processRegistration();
                showSuccess(name + " registered as " + p.getPatientId()+"!");
                root.setCenter(buildRegistryPage());

                nameField.clear();
                ageField.clear();
                gender.getSelectionModel().clearSelection();
                reportField.clear();
                severity.getSelectionModel().clearSelection();
                bloodType.getSelectionModel().clearSelection();
                allergyField.clear();
                contactField.clear();
                insuranceField.getSelectionModel().clearSelection();

            } catch (NumberFormatException ex) {
                showError("Age must be a number !");
            } catch (ERException ex) {
                showError(ex.getMessage());
            }
        });
        //add button to form
        form.getChildren().add(register);
        //add form to body
        body.getChildren().clear(); //clear any previous attempts
        body.getChildren().add(form);
        //add header and body to panel
        panel.getChildren().clear();
        panel.getChildren().addAll(header, body);

        StackPane container = new StackPane(panel);
        container.setPadding(new Insets(0, 10, 40, 10));

        //add panel to page
        page.getChildren().clear();
        page.getChildren().add(container);

        //make sure the button isn't cut off by the bottom of the screen
        ScrollPane sp = new ScrollPane(page);
        sp.setFitToWidth(true);
        sp.setStyle("-fx-background-color:transparent; -fx-background:transparent;");
        
        return sp;
    }

    private ScrollPane buildStatisticsPage() {
        system.getStats().loadFromPatients(system.getRegistry().getAllPatients()); //refresh for latest
        Stats sa = system.getStats();
        List<Patient> allPatients = system.getRegistry().getAllPatients();

        VBox page = new VBox(20);
        page.setPadding(new Insets(20));
        page.setAlignment(Pos.TOP_CENTER);
        //title
        page.getChildren().add(label("STATISTICS", 24, GREEN_DARK, true));

        //dynamic cards
        HBox cards = new HBox(12);
        cards.setAlignment(Pos.CENTER);
        cards.getChildren().addAll(
            statPageCard("Avg Wait", String.format("%.0f m", sa.getMean()), GREEN_MID),
            statPageCard("Max Wait", sa.getMax() + " m", "#E74C3C"),
            statPageCard("Total", String.valueOf(sa.getTotalPatients()), GREEN_DARK)

        );

        VBox breakdown = new VBox(8);
        breakdown.setMaxWidth(600);
        breakdown.getChildren().add(label("Wait Time by Severity", 14, GRAY, true));

        String[] severities = {"Critical", "High", "Moderate", "Low"};
        String[] colors = {"#E74C3C", "#E67E22", "#F1C40F", "#2ECC71"};

        //loop so it creates label texts for each
        for (int i = 0; i < severities.length; i++) {
            String labelText = severities[i];
            double avg = sa.getAvgBySeverity(allPatients, labelText);
            long max = sa.getMaxBySeverity(allPatients, labelText);


            HBox row = new HBox(15);
            row.setPadding(new Insets(10, 15, 10, 15));
            row.setAlignment(Pos.CENTER_LEFT);
            row.setStyle("-fx-background-color: white; -fx-background-radius: 10; -fx-border-color: #EEE; -fx-border-radius: 10;");

            Label s = new Label(labelText);
            s.setPrefWidth(100);
            s.setStyle("-fx-font-weight: bold; -fx-text-fill: " + colors[i] + ";");

            row.getChildren().addAll(s, label(String.format("Average: %.0f mins", avg), 13, "#333", false));
            breakdown.getChildren().add(row);

        }
        page.getChildren().addAll(cards, breakdown);
        return shell(page);
    }
    
    private ScrollPane buildRegistryPage() {
        VBox page = new VBox(20);
        page.setPadding(new Insets(34, 28, 34, 28));

        VBox panel = whitePanel();

        VBox header = pageHeader("PATIENT REGISTRY");

        TextField search = new TextField();
        search.setPromptText("Patient ID or Name");
        search.setMaxWidth(360);
        search.setPrefHeight(46);
        search.setStyle("-fx-background-color: " + PANEL + "; -fx-border-color: #D3D7A6; -fx-background-radius: 10; -fx-border-radius: 10; -fx-font-size: 14px; -fx-padding: 10 14 10 14;");

        VBox list = new VBox(12);
        for (Patient p : system.getRegistry().getAllPatients()) {
            list.getChildren().add(registryRow(p));
        }

        search.textProperty().addListener((obs, old, val) -> {
            list.getChildren().clear();
            for (Patient p : system.getRegistry().getAllPatients()) {
                if (p.getName().toLowerCase().contains(val.toLowerCase()) || p.getPatientId().toLowerCase().contains(val.toLowerCase())) {
                    list.getChildren().add(registryRow(p));
                }
            }
        });

        panel.getChildren().addAll(header, search, list);
        page.getChildren().add(panel);
        return shell(page);
    }

    private VBox registryRow(Patient p) {
        VBox container = new VBox(0);

        HBox row = new HBox(16);
        row.setAlignment(Pos.CENTER_LEFT);
        row.setPadding(new Insets(16, 22, 16, 22));
        row.setStyle("-fx-background-color: " + WHITE + ";"
                + "-fx-background-radius: 18;"
                + "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.08), 12, 0, 0, 3);"
                + "-fx-cursor: hand;");

        Button dischargeBtn = new Button("Discharge");
        dischargeBtn.setStyle("-fx-background-color: #E74C3C; -fx-text-fill: white; -fx-font-weight: bold;-fx-background-radius: 8;");
        dischargeBtn.setOnAction(e -> {
            e.consume();
            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
            confirm.setTitle("Discharge Patient");
            confirm.setHeaderText(null);
            confirm.setContentText("Are you sure you want to discharge this patient? Please confirm your decision again !!");
            confirm.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    system.dischargePatient(p.getPatientId());
                    showSuccess(p.getName() + " has been discharged.");
                    root.setCenter(buildRegistryPage());
                }
            });
        });

        Label id = new Label(p.getPatientId());
        id.setStyle("-fx-font-size: 12px; -fx-font-weight: bold; -fx-text-fill: " + GREEN_DARK + ";"
                + "-fx-background-color: " + GREEN_LIGHT + ";"
                + "-fx-padding: 5 10 5 10; -fx-background-radius: 8;");

        Label avatar = new Label("🧒🏻");
        avatar.setStyle("-fx-font-size: 26px;");

        VBox info = new VBox(3);
        info.getChildren().addAll(
                label(p.getName() + ", " + p.getAge() + " " + p.getGender(), 18, "#222222", true),
                label(p.getCondition(), 14, GRAY, false)
        );
        HBox.setHgrow(info, Priority.ALWAYS);

        VBox assign = new VBox(3);
        //only show the ID if the patient is actually allocated
        String roomDisplay = p.isAssigned() ? p.getRoomId() : "Waiting...";
        String docDisplay = p.isAssigned() ? p.getDoctorId() : "Unassigned";

        assign.getChildren().addAll(
                label("Room: " + roomDisplay, 13, "#555555", false),
                label("Doctor: " + docDisplay, 13, "#555555", false)
        );

        Label arrow = new Label("▾");
        arrow.setStyle("-fx-font-size: 16px; -fx-text-fill: " + GRAY + ";");

        //added spacing to the discharge button to make it look cleaner in the row
        dischargeBtn.setMinWidth(90);

        row.getChildren().addAll(id, avatar, info, assign, arrow, dischargeBtn);

        VBox details = new VBox(8);
        details.setPadding(new Insets(16, 22, 18, 72));
        details.setStyle("-fx-background-color: #F8FCF0; -fx-background-radius: 0 0 18 18; -fx-border-color: #D8E8C0; -fx-border-width: 0 1 1 1; -fx-border-radius: 0 0 18 18;");
        details.setVisible(false);
        details.setManaged(false);

        //added another detailItem for Status so you can see it in the expanded view too
        details.getChildren().addAll(
                detailItem("Status", p.isAssigned() ? "Allocated" : "In Queue"),
                detailItem("Blood Type", p.getBloodType()),
                detailItem("Insurance", p.getInsurance()),
                detailItem("Allergies", p.getAllergies()),
                detailItem("Contact", p.getContact()),
                detailItem("Arrival", p.getArrivalTime().toString()),
                detailItem("Severity", p.getSeverityScore() + "/10 (" + p.getSeverityLabel() + ")")
        );

        row.setOnMouseClicked(e -> {
            //check if the user clicked the button specifically - if not, expand the row
            if (e.getTarget() != dischargeBtn) {
                boolean show = !details.isVisible();
                details.setVisible(show);
                details.setManaged(show);
                arrow.setText(show ? "▴" : "▾");
            }
        });

        container.getChildren().addAll(row, details);
        return container;
    }

    private VBox statPageCard(String title, String value, String color) {
        VBox card = new VBox(6);
        card.setPadding(new Insets(16));
        card.setPrefWidth(180);
        card.setStyle(
            "-fx-background-color: " + WHITE + ";" +
            "-fx-background-radius: 16;" +
            "-fx-effect: dropshadow(gaussian,rgba(0,0,0,0.08),8,0,0,3);"
        );

        card.getChildren().addAll(
            label(title, 12, GRAY, true),
            label(value, 20, color, true)
        );

        return card;
    }
    
    private HBox detailItem(String label, String value) {
        HBox item = new HBox(10);
        item.setAlignment(Pos.CENTER_LEFT);
        Label lbl = new Label(label + ":");
        lbl.setStyle("-fx-font-size: 12px; -fx-text-fill: " + GRAY + "; -fx-min-width: 160px;");
        Label val = new Label(value);
        val.setStyle("-fx-font-size: 12px; -fx-font-weight: bold; -fx-text-fill: #333333;");
        item.getChildren().addAll(lbl, val);
        return item;
    }

    private ScrollPane buildDashboard() {
        VBox outerFrame = new VBox();
        outerFrame.setPadding(new Insets(18));
        outerFrame.setStyle("-fx-background-color: " + GREEN_BG + "; -fx-background-radius: 34;");

        VBox whiteCard = new VBox(26);
        whiteCard.setPadding(new Insets(30));
        whiteCard.setStyle("-fx-background-color: " + WHITE + "; -fx-background-radius: 30;");
        whiteCard.setMaxWidth(800);

        HBox header = new HBox();
        header.setAlignment(Pos.CENTER_LEFT);

        VBox headings = new VBox(2);
        headings.getChildren().addAll(
                label("DASHBOARD", 34, GREEN_DARK, true),
                label("Hi " + userName + " !!", 20, GREEN_MID, true)
        );

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        Label avatar = new Label("👩");
        avatar.setStyle("-fx-font-size: 36px;");
        header.getChildren().addAll(headings, spacer, avatar);

        int critical = system.getTriage().countCritical();
        int high = system.getTriage().countHigh();
        int moderate = system.getTriage().countModerate();
        int low = system.getTriage().countLow();
        int doctors = system.getResourceGraph().countAvailableDoctors();
        int rooms = system.getResourceGraph().countAvailableRooms();

        HBox statCards = new HBox(18);
        statCards.setAlignment(Pos.CENTER_LEFT);
        HBox.setHgrow(statCards, Priority.ALWAYS);

        statCards.getChildren().addAll(
                statCard("CRITICAL PATIENTS", "Allocated: " + critical, "#E74C3C"),
                statCard("WAITING", "Critical: " + critical + "\nHigh: " + high + "\nModerate: " + moderate + "\nLow: " + low, GREEN_MID),
                statCardBig("DOCTORS AVAILABLE", String.valueOf(doctors), GREEN_MID),
                statCardBig("VACANT ROOMS", String.valueOf(rooms), GREEN_MID)
        );

        HBox bottom = new HBox(18);
        bottom.getChildren().addAll(buildMiniQueue(), buildMiniStats(), buildCalendar());

        whiteCard.getChildren().addAll(header, statCards, bottom);
        StackPane centerWrap = new StackPane(whiteCard);
        StackPane.setAlignment(whiteCard, Pos.TOP_CENTER);
        outerFrame.getChildren().add(centerWrap);

        ScrollPane sp = new ScrollPane(outerFrame);
        sp.setFitToWidth(true);
        sp.setStyle("-fx-background-color: transparent; -fx-background: transparent; -fx-border-color: transparent;");
        return sp;
    }

    private VBox buildMiniQueue() {
        VBox card = new VBox(10);
        card.setPadding(new Insets(18));
        card.setPrefWidth(310);
        card.setStyle("-fx-background-color: #D8EF9B; -fx-background-radius: 20;");
        card.getChildren().add(label("NEXT IN QUEUE", 13, GREEN_DARK, true));

        List<Patient> patients = system.getTriage().getAllPatientsSorted();
        int count = Math.min(4, patients.size());
        for (int i = 0; i < count; i++) {
            Patient p = patients.get(i);
            card.getChildren().add(queueRowMini(p));
        }
        return card;
    }

    private HBox queueRowMini(Patient p) {
        HBox row = new HBox(10);
        row.setAlignment(Pos.CENTER_LEFT);
        row.setPadding(new Insets(8, 10, 8, 10));
        row.setStyle("-fx-background-color: " + WHITE + "; -fx-background-radius: 10;");

        Label av = new Label("👤");
        av.setStyle("-fx-font-size: 22px;");

        VBox info = new VBox(2);
        info.getChildren().addAll(
                label(p.getName(), 12, "#333333", true),
                label(p.getCondition(), 11, GRAY, false)
        );
        HBox.setHgrow(info, Priority.ALWAYS);

        Label sev = new Label(p.getSeverityLabel());
        sev.setStyle("-fx-background-color: " + p.getSeverityColor() + "; -fx-text-fill: white; -fx-font-size: 11px; -fx-font-weight: bold; -fx-padding: 6 10 6 10; -fx-background-radius: 20;");

        row.getChildren().addAll(av, info, sev);
        return row;
    }

    private VBox buildMiniStats() {
        VBox card = new VBox(8);
        card.setPadding(new Insets(18));
        card.setPrefWidth(250);
        card.setStyle("-fx-background-color: #D8EF9B; -fx-background-radius: 20;");
        card.getChildren().add(label("STATISTICS", 13, GREEN_DARK, true));

        Stats sa = system.getStats();
        card.getChildren().addAll(
                miniStat("Average Wait Time", String.format("%.0f mins", sa.getMean())),
                miniStat("Longest Wait", sa.getMax() + " mins"),
                miniStat("Total Patients", String.valueOf(sa.getTotalPatients())),
                miniStat("Shortest Wait", sa.getMin() + " mins")
        );
        return card;
    }

    private VBox miniStat(String a, String b) {
        VBox row = new VBox(2);
        row.setPadding(new Insets(8, 12, 8, 12));
        row.setStyle("-fx-background-color: " + WHITE + "; -fx-background-radius: 10;");
        row.getChildren().addAll(label(a, 11, "#333333", true), label(b, 13, GREEN_MID, true));
        return row;
    }

    private VBox buildCalendar() {
        VBox card = new VBox(10);
        card.setPrefWidth(210);
        card.setStyle("-fx-background-color: transparent;");

        card.getChildren().add(label("List of appointments", 12, "#333333", true));

        HBox tabs = new HBox(8);
        Button monthly = new Button("Monthly");
        monthly.setStyle("-fx-background-color: " + WHITE + "; -fx-text-fill: #333333; -fx-font-size: 11px; -fx-background-radius: 8; -fx-padding: 5 10 5 10; -fx-border-color: #CCCCCC; -fx-border-radius: 8;");
        Button daily = new Button("Daily");
        daily.setStyle("-fx-background-color: transparent; -fx-text-fill: " + GRAY + "; -fx-font-size: 11px; -fx-background-radius: 8; -fx-padding: 5 10 5 10;");
        tabs.getChildren().addAll(monthly, daily);

        VBox calBox = new VBox(6);
        calBox.setPadding(new Insets(12));
        calBox.setStyle("-fx-background-color: " + WHITE + "; -fx-background-radius: 14; -fx-effect: dropshadow(gaussian,rgba(0,0,0,0.07),8,0,0,3);");

        LocalDate now = LocalDate.now();
        String monthName = now.getMonth().toString().charAt(0) + now.getMonth().toString().substring(1).toLowerCase();
        calBox.getChildren().add(label(monthName + " " + now.getYear(), 13, "#333333", true));

        HBox dayHeaders = new HBox(4);
        for (String d : new String[]{"S", "M", "T", "W", "T", "F", "S"}) {
            Label dl = new Label(d);
            dl.setPrefWidth(24);
            dl.setAlignment(Pos.CENTER);
            dl.setStyle("-fx-font-size: 10px; -fx-text-fill: " + GRAY + ";");
            dayHeaders.getChildren().add(dl);
        }
        calBox.getChildren().add(dayHeaders);

        int startDay = now.withDayOfMonth(1).getDayOfWeek().getValue() % 7;
        int daysInMonth = now.lengthOfMonth();
        int day = 1;

        for (int row = 0; row < 6 && day <= daysInMonth; row++) {
            HBox week = new HBox(4);
            for (int col = 0; col < 7; col++) {
                if ((row == 0 && col < startDay) || day > daysInMonth) {
                    Label empty = new Label("");
                    empty.setPrefWidth(24);
                    week.getChildren().add(empty);
                } else {
                    Label dl = new Label(String.valueOf(day));
                    dl.setPrefSize(24, 24);
                    dl.setAlignment(Pos.CENTER);
                    if (day == now.getDayOfMonth()) {
                        dl.setStyle("-fx-background-color: " + GREEN_MID + "; -fx-text-fill: white; -fx-font-size: 10px; -fx-font-weight: bold; -fx-background-radius: 12;");
                    } else {
                        dl.setStyle("-fx-font-size: 10px; -fx-text-fill: #333333;");
                    }
                    week.getChildren().add(dl);
                    day++;
                }
            }
            calBox.getChildren().add(week);
        }

        card.getChildren().addAll(tabs, calBox);
        return card;
    }

    private VBox statCard(String title, String content, String color) {
        VBox card = new VBox(8);
        card.setPadding(new Insets(18));
        card.setPrefWidth(200);
        card.setPrefHeight(130);
        card.setStyle("-fx-background-color: " + WHITE + "; -fx-background-radius: 16; -fx-effect: dropshadow(gaussian,rgba(0,0,0,0.07),8,0,0,3);");
        card.getChildren().addAll(label(title, 11, GRAY, true), label(content, 13, color, true));
        return card;
    }

    private VBox statCardBig(String title, String value, String color) {
        VBox card = new VBox(8);
        card.setPadding(new Insets(18));
        card.setPrefWidth(200);
        card.setPrefHeight(130);
        card.setStyle("-fx-background-color: " + WHITE + "; -fx-background-radius: 16; -fx-effect: dropshadow(gaussian,rgba(0,0,0,0.07),8,0,0,3);");
        card.getChildren().addAll(label(title, 11, GRAY, true), label(value, 36, color, true));
        return card;
    }

    private void showError(String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }

    private void showSuccess(String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
    public static void main(String[] args) {
        launch(args);
    }
}