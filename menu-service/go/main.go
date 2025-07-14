package main

import (
	"database/sql"
	"fmt"
	"log"
	"net/http"
	"os"

	_ "github.com/lib/pq"

	"github.com/gorilla/mux"
	"github.com/joho/godotenv"
	"github.com/rs/cors"
)

type Status string

const (
	Processing Status = "Processing"
	Ready      Status = "Ready"
	Failed     Status = "Failed"
)

type Menu struct {
	ID              int     `json:"id"`
	ItemName        string  `json:"itemName"`
	ItemPrice       float64 `json:"itemPrice"`
	DefaultSpiceLevel int     `json:"defaultSpiceLevel"`
	TagLine         string  `json:"tagLine"`
	ItemImageURL    string  `json:"itemImageURL"`
	ItemThumbnailURL string `json:"itemThumbnailURL"`
	ItemStatus      Status  `json:"itemStatus"`
}

func main() {
	fmt.Println("Starting the application...")

	port := os.Getenv("PORT")
	if port == "" {
		port = "8080"
	}

	r := mux.NewRouter()

	r.HandleFunc("/", func(w http.ResponseWriter, r *http.Request) {
		fmt.Fprintln(w, "Hello, World!")
	})

	r.HandleFunc("/menu", func(w http.ResponseWriter, r *http.Request) {
		w.WriteHeader(http.StatusOK)
		//fmt.Fprintln(w, "Getting all menu items")
		log.Println("Getting all menu items")
		var menuItems []Menu
		var err error
		var rows *sql.Rows
		rows, err = db.Query("select * from menu")
		if err != nil {
			log.Fatal(err)
		}
		for _, row := range rows.Data() {
			menuItems = append(menuItems, Menu{
				ID:              row.Int64,
				ItemName:        row.String,
				ItemPrice:       row.Float64,
				DefaultSpiceLevel: row.Int64,
				TagLine:         row.String,
				ItemImageURL:    row.String,
				ItemThumbnailURL: row.String,
				ItemStatus:      Status(row.String),
			})
		}

		//fmt.Fprintln(w, "Getting all menu items")
		log.Println("Getting all menu items")

		//json.NewEncoder(w).Encode(menuItems)
	})

	r.HandleFunc("/menu/{id}", func(w http.ResponseWriter, r *http.Request) {
		w.WriteHeader(http.StatusOK)
		//fmt.Fprintln(w, "Getting menu item by id")
		log.Println("Getting menu item by id")
		vars := mux.Vars(r)
		id := vars["id"]
		log.Println(id)
		var menuItems []Menu
		var err error
		var rows *sql.Rows
		rows, err = db.Query("select * from menu where id = %d", id)
		if err != nil {
			log.Fatal(err)
		}
		for _, row := range rows.Data() {
			menuItems = append(menuItems, Menu{
				ID:              row.Int64,
				ItemName:        row.String,
				ItemPrice:       row.Float64,
				DefaultSpiceLevel: row.Int64,
				TagLine:         row.String,
				ItemImageURL:    row.String,
				ItemThumbnailURL: row.String,
				ItemStatus:      Status(row.String),
			})
		}
		//fmt.Fprintln(w, "Getting menu item by id")
		log.Println("Getting all menu item by id")
		//json.NewEncoder(w).Encode(menuItems)
	})

	r.HandleFunc("/menu/ready", func(w http.ResponseWriter, r *http.Request) {
		w.WriteHeader(http.StatusOK)
		//fmt.Fprintln(w, "Getting all ready menu items")
		log.Println("Getting all ready menu items")
		var menuItems []Menu
		var err error
		var rows *sql.Rows
		rows, err = db.Query("select * from menu where status = 'Ready'")
		if err != nil {
			log.Fatal(err)
		}
		for _, row := range rows.Data() {
			menuItems = append(menuItems, Menu{
				ID:              row.Int64,
				ItemName:        row.String,
				ItemPrice:       row.Float64,
				DefaultSpiceLevel: row.Int64,
				TagLine:         row.String,
				ItemImageURL:    row.String,
				ItemThumbnailURL: row.String,
				ItemStatus:      Status(row.String),
			})
		}
		//fmt.Fprintln(w, "Getting all ready menu items")
		log.Println("Getting all ready menu items")
		//json.NewEncoder(w).Encode(menuItems)
	})

	r.HandleFunc("/menu/failed", func(w http.ResponseWriter, r *http.Request) {
		w.WriteHeader(http.StatusOK)
		//fmt.Fprintln(w, "Getting all failed menu items")
		log.Println("Getting all failed menu items")
		var menuItems []Menu
		var err error
		var rows *sql.Rows
		rows, err = db.Query("select * from menu where status = 'Failed'")
		if err != nil {
			log.Fatal(err)
		}
		for _, row := range rows.Data() {
			menuItems = append(menuItems, Menu{
				ID:              row.Int64,
				ItemName:        row.String,
				ItemPrice:       row.Float64,
				DefaultSpiceLevel: row.Int64,
				TagLine:         row.String,
				ItemImageURL:    row.String,
				ItemThumbnailURL: row.String,
				ItemStatus:      Status(row.String),
			})
		}
		//fmt.Fprintln(w, "Getting all failed menu items")
		log.Println("Getting all failed menu items")
		//json.NewEncoder(w).Encode(menuItems)
	})

	r.HandleFunc("/menu/processing", func(w http.ResponseWriter, r *http.Request) {
		w.WriteHeader(http.StatusOK)
		//fmt.Fprintln(w, "Getting all processing menu items")
		log.Println("Getting all processing menu items")
		var menuItems []Menu
		var err error
		var rows *sql.Rows
		rows, err = db.Query("select * from menu where status = 'Processing'")
		if err != nil {
			log.Fatal(err)
		}
		for _, row := range rows.Data() {
			menuItems = append(menuItems, Menu{
				ID:              row.Int64,
				ItemName:        row.String,
				ItemPrice:       row.Float64,
				DefaultSpiceLevel: row.Int64,
				TagLine:         row.String,
				ItemImageURL:    row.String,
				ItemThumbnailURL: row.String,
				ItemStatus:      Status(row.String),
			})
		}
		//fmt.Fprintln(w, "Getting all processing menu items")
		log.Println("Getting all processing menu items")
		//json.NewEncoder(w).Encode(menuItems)
	})
	// start database connection
	var db *sql.DB
	godotenv.Load()
	log.Println("Opening database connection")

	log.Println("Database connection initialized")
	db, err := sql.Open("postgres", "user=postgres, password=postgres")
	if err != nil {
		panic(err)
	}
	// make cors connection
	cors := cors.New(cors.Options{
		AllowedOrigins: []string{"*"},
		AllowedMethods: []string{"GET", "POST", "PUT", "DELETE"},
		Debug: true,
	})
	defer cors.Close()

import (
	"database/sql"
	"fmt"
	"log"
	"net/http"
	"os"
	"encoding/json"

	_ "github.com/lib/pq"

	"github.com/gorilla/mux"
	"github.com/joho/godotenv"
	"github.com/rs/cors"
)
type Status string

const (
	Processing Status = "Processing"
	Ready      Status = "Ready"
	Failed     Status = "Failed"
)

type Menu struct {
	ID              int     `json:"id"`
	ItemName        string  `json:"itemName"`
	ItemPrice       float64 `json:"itemPrice"`
	DefaultSpiceLevel int     `json:"defaultSpiceLevel"`
	TagLine         string  `json:"tagLine"`
	ItemImageURL    string  `json:"itemImageURL"`
	ItemThumbnailURL string `json:"itemThumbnailURL"`
	ItemStatus      Status  `json:"itemStatus"`
}

func getMenuItems(w http.ResponseWriter, r *http.Request, db *sql.DB) {
	log.Println("Getting all menu items")
	var menuItems []Menu
	var err error
	var rows *sql.Rows
	rows, err = db.Query("select * from menu")
	if err != nil {
		log.Fatal(err)
	}
	for rows.Next() {
		var menu Menu
		err = rows.Scan(&menu.ID, &menu.ItemName, &menu.ItemPrice, &menu.DefaultSpiceLevel, &menu.TagLine, &menu.ItemImageURL, &menu.ItemThumbnailURL, &menu.ItemStatus)
		if err != nil {
			log.Fatal(err)
		}
		menuItems = append(menuItems, menu)
	}
	json.NewEncoder(w).Encode(menuItems)
}

func getMenuItem(w http.ResponseWriter, r *http.Request, db *sql.DB) {
	log.Println("Getting menu item by id")
	vars := mux.Vars(r)
	id := vars["id"]
	log.Println(id)
	var menuItems []Menu
	var err error
	var rows *sql.Rows
	rows, err = db.Query("select * from menu where id = $1", id)
	if err != nil {
		log.Fatal(err)
	}
	for rows.Next() {
		var menu Menu
		err = rows.Scan(&menu.ID, &menu.ItemName, &menu.ItemPrice, &menu.DefaultSpiceLevel, &menu.TagLine, &menu.ItemImageURL, &menu.ItemThumbnailURL, &menu.ItemStatus)
		if err != nil {
			log.Fatal(err)
		}
		menuItems = append(menuItems, menu)
	}
	json.NewEncoder(w).Encode(menuItems)
}
func getMenuItemByStatus(w http.ResponseWriter, r *http.Request, status string, db *sql.DB) {
	log.Println("Getting all menu items with status " + status)
	var menuItems []Menu
	var err error
	var rows *sql.Rows
	rows, err = db.Query("select * from menu where item_status = $1", status)
	if err != nil {
		log.Fatal(err)
	}
	for rows.Next() {
		var menu Menu
		err = rows.Scan(&menu.ID, &menu.ItemName, &menu.ItemPrice, &menu.DefaultSpiceLevel, &menu.TagLine, &menu.ItemImageURL, &menu.ItemThumbnailURL, &menu.ItemStatus)
		if err != nil {
			log.Fatal(err)
		}
		menuItems = append(menuItems, menu)
	}
	json.NewEncoder(w).Encode(menuItems)
}

func createMenuItem(w http.ResponseWriter, r *http.Request, db *sql.DB) {
	log.Println("Creating a menu item")
	var menu Menu
	err := json.NewDecoder(r.Body).Decode(&menu)
	if err != nil {
		log.Fatal(err)
	}
	_, err = db.Exec("INSERT INTO menu (item_name, item_price, default_spice_level, tag_line, item_image_url, item_thumbnail_url, item_status) VALUES ($1, $2, $3, $4, $5, $6, $7)", menu.ItemName, menu.ItemPrice, menu.DefaultSpiceLevel, menu.TagLine, menu.ItemImageURL, menu.ItemThumbnailURL, menu.ItemStatus)
	if err != nil {
		log.Fatal(err)
	}
	log.Println("Creating a menu item")
	w.WriteHeader(http.StatusCreated)
	w.WriteHeader(http.StatusOK)
}
func updateMenuItem(w http.ResponseWriter, r *http.Request, db *sql.DB) {
	log.Println("Updating menu item")
	vars := mux.Vars(r)
	id := vars["id"]
	log.Println(id)
	var menu Menu
	err := json.NewDecoder(r.Body).Decode(&menu)
	if err != nil {
		log.Fatal(err)
	}
	_, err = db.Exec("UPDATE menu SET item_name = $1, item_price = $2, default_spice_level = $3, tag_line = $4, item_image_url = $5, item_thumbnail_url = $6, item_status = $7 WHERE id = $8", menu.ItemName, menu.ItemPrice, menu.DefaultSpiceLevel, menu.TagLine, menu.ItemImageURL, menu.ItemThumbnailURL, menu.ItemStatus, id)
	if err != nil {
		log.Fatal(err)
	}
	log.Println("Updating menu item")
	w.WriteHeader(http.StatusOK)
}
func deleteMenuItem(w http.ResponseWriter, r *http.Request, db *sql.DB) {
	log.Println("Deleting menu item")
	vars := mux.Vars(r)
	id := vars["id"]
	log.Println(id)
	_, err := db.Exec("DELETE FROM menu WHERE id = $1", id)
	if err != nil {
		log.Fatal(err)
	}
	log.Println("Deleting menu item")
	w.WriteHeader(http.StatusNoContent)
}

func main() {
	fmt.Println("Starting the application...")

	port := os.Getenv("PORT")
	if port == "" {
		port = "8080"
	}
	var db *sql.DB
	godotenv.Load()
	log.Println("Opening database connection")

	log.Println("Database connection initialized")
	db, err := sql.Open("postgres", os.Getenv("DATABASE_URL"))
	if err != nil {
		panic(err)
	}
	// make cors connection
	c := cors.New(cors.Options{
		AllowedOrigins: []string{"*"},
		AllowedMethods: []string{"GET", "POST", "PUT", "DELETE"},
		Debug: true,
	})

	handler := c.Handler(r)
	defer db.Close()

	err = db.Ping()
	if err != nil {
		log.Fatalf("Error pinging database: %v", err)
	}
	log.Println("Database connection initialized")

	r := mux.NewRouter()
	r.HandleFunc("/menu", func(w http.ResponseWriter, r *http.Request) {
		if r.Method == "POST"{
			createMenuItem(w,r,db)
		} else{
			getMenuItems(w,r,db)
		}
	})
	r.HandleFunc("/menu/{id}", func(w http.ResponseWriter, r *http.Request) {
		if r.Method == "PUT" {
			updateMenuItem(w, r, db)
		} else if r.Method == "GET"{
			getMenuItem(w, r, db)
		} else if r.Method == "DELETE"{
			deleteMenuItem(w, r, db)
		}
	})
	r.HandleFunc("/menu/ready", func(w http.ResponseWriter, r *http.Request) {
		getMenuItemByStatus(w, r, "Ready", db)
	})
	r.HandleFunc("/menu/failed", func(w http.ResponseWriter, r *http.Request) {
		getMenuItemByStatus(w, r, "Failed", db)
	})
	r.HandleFunc("/menu/processing", func(w http.ResponseWriter, r *http.Request) {
		getMenuItemByStatus(w, r, "Processing", db)
	})

	log.Printf("Server started on port %s\n", port)
	log.Fatal(http.ListenAndServe(":"+port, handler))
}
	log.Fatal(http.ListenAndServe(":"+port, handler))
}
	// start database connection
	var db *sql.DB
	godotenv.Load()
	log.Println("Opening database connection")

	log.Println("Database connection initialized")
	db, err := sql.Open("postgres", "user=postgres, password=postgres")
	if err != nil {
		panic(err)
	}
	// make cors connection
	cors := cors.New(cors.Options{
		AllowedOrigins: []string{"*"},
	})
	defer db.Close()

	err = db.Ping()
	if err != nil {
		log.Fatalf("Error pinging database: %v", err)
	}
	log.Println("Database connection initialized")

	// start http server
	c := cors.New(cors.Options{
		AllowedOrigins: []string{"*"},
		AllowedMethods: []string{"GET", "POST", "PUT", "DELETE"},
		Debug: true,
	})

	handler := c.Handler(r)

	log.Printf("Server started on port %s\n", port)
	log.Fatal(http.ListenAndServe(":"+port, handler))
}
