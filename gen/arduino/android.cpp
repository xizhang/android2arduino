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
    
    switch(id) {
        case 0: {
            int pin0 = ReadInt();
            int mode0 = ReadInt();
            pinMode(pin0, mode0);
            break;
        }
        case 1: {
            int pin1 = ReadInt();
            int value1 = ReadInt();
            digitalWrite(pin1, value1);
            break;
        }
        case 2: {
            int pin2 = ReadInt();
            int value2 = ReadInt();
            analogWrite(pin2, value2);
            break;
        }
        case 3: {
            int pin3 = ReadInt();
            int returnValue3 = digitalRead(pin3);
            break;
        }
        case 4: {
            int pin4 = ReadInt();
            int returnValue4 = analogRead(pin4);
            break;
        }
    }

}

void setup() {
    // Generated code.
    // _SETUP_
}

void loop() {
    BufferSerialInput();
    HandleCommand();
}
