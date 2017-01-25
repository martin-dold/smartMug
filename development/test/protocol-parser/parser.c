#include <stdio.h>
#include <unistd.h>
#include <stdint.h>

typedef struct
{
  uint8_t tag;
  uint8_t length;
  uint8_t value[10];
} S_MESSAGE_t;

typedef enum
{
  E_PARSER_STATE_READ_TAG,
  E_PARSER_STATE_READ_LENGTH,
  E_PARSER_STATE_READ_VALUE,
  E_PARSER_STATE_READ_EOF,
} E_PARSER_STATE_t;


void displayMessage(S_MESSAGE_t *message)
{
  uint16_t sensorValue = 0U;

  printf("Message received: ");
  if(message->tag == 1U)
  {
    sensorValue = (uint16_t)( (message->value[0]) << 8 );
    sensorValue += (message->value[1]);
    printf("Tag = %u, Length = %u, Value = %u\n", message->tag, message->length, sensorValue);
  }
  else
  {
    printf("Unknown message tag");
  }
}

int main()
{
  uint8_t byte;
  uint8_t byteCount = 0U;

  E_PARSER_STATE_t state = E_PARSER_STATE_READ_TAG;
  S_MESSAGE_t message;

  printf("SmartMug Protocol Parser Start.\n");

  while( read(0, &byte, 1) > 0)
  {
    switch(state)
    {
      case E_PARSER_STATE_READ_TAG:
        message.tag = byte;
        byteCount = 0U;
        state = E_PARSER_STATE_READ_LENGTH;
        break;

      case E_PARSER_STATE_READ_LENGTH:
        message.length = byte;
        state = E_PARSER_STATE_READ_VALUE;
        break;

      case E_PARSER_STATE_READ_VALUE:
        message.value[byteCount] = byte;
        byteCount++;
        if(byteCount == message.length)
        {
          state = E_PARSER_STATE_READ_EOF;
        }
        break;

      case E_PARSER_STATE_READ_EOF:
        if(byte == '\n')
        {
          state = E_PARSER_STATE_READ_TAG;
          displayMessage(&message);
        }
        else
        {
          /* Error! */
        }
        break;

      default:
        break;
    }
  }

  printf("SmartMug Protocol Parser Exit.\n");
}
