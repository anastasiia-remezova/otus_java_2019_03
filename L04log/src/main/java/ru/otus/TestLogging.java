package ru.otus;

class TestLogging implements TestLoginInterface {
    @Log
    public void calculation(int param) {
        System.out.println("Exec Test Logging");
    };

    @Log
    public void calculation(int param, int param2) {
        System.out.println("Exec Test Logging 2");
    };


    public void plus(int param) {
        System.out.println("Exec plus");
    };

}
