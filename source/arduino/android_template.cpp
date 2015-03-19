#define INVALID  -1

int object_ids[10];
int object_addresses[10];

char buffer[10];
int read_index = 0;

// mocks

int digitalRead(int pin) {
    return 0;
}

int analogRead(int pin) {
    return 0;
}

void analogWrite(int pin, int value) {
    
}

void digitalWrite(int pin, int value) {
    
}

void pinMode(int pin, int mode) {
    
}

int main() {
    return 0;   
}

//

int GetAddressById() {
    
}

int ReadInt() {
    return 0;
}

void BufferSerialInput() {

}

void HandleCommand() {
    read_index = 0;
    int id = ReadInt();
    // Generated code.
    // _BIG_SWITCH_
}

void setup() {
    // Generated code.
    // _SETUP_
}

void loop() {
    BufferSerialInput();
    HandleCommand();
}
