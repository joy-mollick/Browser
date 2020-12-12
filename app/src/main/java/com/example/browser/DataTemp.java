package com.example.browser;

public class DataTemp {

        private int id;
        private String st;
        private String tm;


        /// It is constructor of this structure (class)
        public DataTemp(String s, String t) {
            st = s;
            tm = t;
        }

        /// rest of all are setter and getter methods
        public void setstr(String str) {
            this.st = str;
        }

        public String getstr() {
            return st;
        }

        public void setTime(String time) {
            this.tm = time;
        }

        public String getTime() {
            return tm;
        }
    }
