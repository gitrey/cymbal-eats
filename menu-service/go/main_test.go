package main

import (
	"net/http"
	"net/http/httptest"
	"testing"
)

func TestServer(t *testing.T) {
	t.Run("Server is running", func(t *testing.T) {
		req, err := http.NewRequest("GET", "/")
		if err != nil {
			t.Fatalf("Failed to create request: %v", err)
		}
		rr := httptest.NewRecorder(req)
		handler := http.HandlerFunc(func(w http.ResponseWriter, r *http.Request){
			fmt.Fprintln(w, "Hello, World!")
		})
		
		if err != nil {
			t.Fatalf("Failed to create request: %v", err)
		}
		rr := httptest.NewRecorder()
		
		r := mux.NewRouter()
		r.HandleFunc("/menu", getMenuItems)
		handler := r
		handler.ServeHTTP(rr, req)

		if status := rr.Code; status != http.StatusOK {
			t.Errorf("handler returned wrong status code: got %v want %v",
		}

	})
	t.Run("Get menu item by id is running", func(t *testing.T) {
		req, err := http.NewRequest("GET", "/menu/1")
		if err != nil {
			t.Fatalf("Failed to create request: %v", err)
		}
		rr := httptest.NewRecorder()

		r := mux.NewRouter()
		r.HandleFunc("/menu/{id}", getMenuItem)
		handler := r
		handler.ServeHTTP(rr, req)

		if status := rr.Code; status != http.StatusOK {
			t.Errorf("handler returned wrong status code: got %v want %v",
				status, http.StatusOK)
		}

	})
	t.Run("Get menu items by status is running", func(t *testing.T) {
		req, err := http.NewRequest("GET", "/menu/ready")
		if err != nil {
			t.Fatalf("Failed to create request: %v", err)
		}
		rr := httptest.NewRecorder()

		r := mux.NewRouter()
		r.HandleFunc("/menu/ready", func(w http.ResponseWriter, r *http.Request){
			getMenuItemByStatus(w, r, "Ready", db)
		})
		r.HandleFunc("/menu/failed", func(w http.ResponseWriter, r *http.Request){
			getMenuItemByStatus(w, r, "Failed", db)
		})
		r.HandleFunc("/menu/processing", func(w http.ResponseWriter, r *http.Request){
			getMenuItemByStatus(w, r, "Processing", db)
		})
		r.HandleFunc("/menu", getMenuItems)
		handler := r
		handler.ServeHTTP(rr, req)

		if status := rr.Code; status != http.StatusOK {
			t.Errorf("handler returned wrong status code: got %v want %v",
				status, http.StatusOK)
		}

		req, err = http.NewRequest("GET", "/menu/failed")
		if err != nil {
			t.Fatalf("Failed to create request: %v", err)
		}
		rr = httptest.NewRecorder()
		handler.ServeHTTP(rr, req)
		if status := rr.Code; status != http.StatusOK {
			t.Errorf("handler returned wrong status code: got %v want %v",
				status, http.StatusOK)
		}

		req, err = http.NewRequest("GET", "/menu/processing")
		if err != nil {
			t.Fatalf("Failed to create request: %v", err)
		}
		rr = httptest.NewRecorder()
		
		handler.ServeHTTP(rr, req)
		if status := rr.Code; status != http.StatusOK {
			t.Errorf("handler returned wrong status code: got %v want %v",
				status, http.StatusOK)
		}

	})
	t.Run("Create menu item is running", func(t *testing.T) {
		req, err := http.NewRequest("POST", "/menu")
		if err != nil {
			t.Fatalf("Failed to create request: %v", err)
		}
		rr := httptest.NewRecorder()

		r := mux.NewRouter()
		r.HandleFunc("/menu", func(w http.ResponseWriter, r *http.Request) {
			if r.Method == "POST" {
				createMenuItem(w, r, db)
			}
		})
		handler := r
		handler.ServeHTTP(rr, req)

		if status := rr.Code; status != http.StatusOK {
			t.Errorf("handler returned wrong status code: got %v want %v",
				status, http.StatusCreated)
		}
	})
	t.Run("Update menu item is running", func(t *testing.T) {
		req, err := http.NewRequest("PUT", "/menu/1")
		if err != nil {
			t.Fatalf("Failed to create request: %v", err)
		}
		rr := httptest.NewRecorder()

		r := mux.NewRouter()
		r.HandleFunc("/menu/{id}", func(w http.ResponseWriter, r *http.Request) {
			if r.Method == "PUT" {
				updateMenuItem(w, r, db)
			}
		})
		handler := r
		handler.ServeHTTP(rr, req)

		if status := rr.Code; status != http.StatusOK {
			t.Errorf("handler returned wrong status code: got %v want %v",
				status, http.StatusOK)
		}
	})
	t.Run("Delete menu item is running", func(t *testing.T) {
		req, err := http.NewRequest("DELETE", "/menu/1")
		if err != nil {
			t.Fatalf("Failed to create request: %v", err)
		}
		rr := httptest.NewRecorder()

		r := mux.NewRouter()
		r.HandleFunc("/menu/{id}", func(w http.ResponseWriter, r *http.Request) {
			if r.Method == "DELETE" {
				deleteMenuItem(w, r, db)
			}
		})
		handler := r
		handler.ServeHTTP(rr, req)

		if status := rr.Code; status != http.StatusNoContent {
			t.Errorf("handler returned wrong status code: got %v want %v",
				status, http.StatusNoContent)
		}
	})
}
