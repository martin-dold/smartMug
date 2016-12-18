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

int main()
{
  uint8_t byte;
  uint8_t byteCount = 0U;
  uint8_t buffer[10] = { 0U };
  E_PARSER_STATE_t state = E_PARSER_STATE_READ_TAG;

  printf("Start.\n");

  while( read(0, &byte, 1) > 0)
  {
    switch(state)
    {
      case E_PARSER_STATE_READ_TAG:
        printf("E_PARSER_STATE_READ_TAG.\n");
        buffer[0] = byte;
        byteCount = 0U;
        state = E_PARSER_STATE_READ_LENGTH;
        break;

      case E_PARSER_STATE_READ_LENGTH:
        printf("E_PARSER_STATE_READ_LENGTH.\n");
        buffer[1] = byte;
        state = E_PARSER_STATE_READ_VALUE;
        break;

      case E_PARSER_STATE_READ_VALUE:
        printf("E_PARSER_STATE_READ_VALUE.\n");
        buffer[2 + byteCount] = byte;
        byteCount++;
        if(byteCount == buffer[1])
        {
          state = E_PARSER_STATE_READ_EOF;
        }
        break;

      case E_PARSER_STATE_READ_EOF:
        printf("E_PARSER_STATE_READ_EOF.\n");
        if(byte == '\n')
        {
          state = E_PARSER_STATE_READ_TAG;
          printf("Message read.\n");
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

  printf("Exit.\n");
}
