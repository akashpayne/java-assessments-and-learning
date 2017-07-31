// MessageReceiver.java (partial implementation)

/**
 * This class implements the receiver side of the data link layer.
 * <P>
 * The source code supplied here contains only a partial implementation.
 * Your completed version must be submitted for assessment.
 * <P>
 * You only need to finish the implementation of the receiveMessage
 * method to complete this class.  No other parts of this file need to
 * be changed.  Do NOT alter the constructor or interface of any public
 * method.  Do NOT put this class inside a package.  You may add new
 * private methods, if you wish, but do NOT create any new classes. 
 * Only this file will be processed when your work is marked.
 */

public class MessageReceiver
{
    // Fields ----------------------------------------------------------

    private FrameReceiver physicalLayer; // physical layer object
    private boolean quiet;               // true=quiet mode (suppress
                                         // prompts and status info)

    // You may add additional fields but this shouldn't be necessary

    // Constructor -----------------------------------------------------

    /**
     * MessageReceiver constructor (DO NOT ALTER ANY PART OF THIS)
     * Create and initialize new MessageReceiver.
     * @param physicalLayer physical layer object with frame receiver service
     * (this will already have been created and initialized by TestReceiver)
     * @param quiet true for quiet mode which suppresses prompts and status info
     */

    public MessageReceiver(FrameReceiver physicalLayer, boolean quiet)
    {
        // Initialize fields and report status

        this.physicalLayer = physicalLayer;
        this.quiet = quiet;
        if (!quiet) {
            System.out.println("Data link layer ready");
        }
    }

    // Methods ---------------------------------------------------------

    /**
     * Receive a message (THIS IS THE ONLY METHOD YOU NEED TO MODIFY)
     * @return the message received, or null if the end of the input
     * stream has been reached.  (See receiveFrame documentation for
     * further explanation of how the end of the input stream is
     * detected and handled.)
     * @throws ProtocolException immediately without attempting to
     * receive any further frames if any errorMessage is detected, such as
     * a corrupt frame being received
     */
	
	public String receiveMessage() throws ProtocolException
    {
        String message = "";
        String frame = physicalLayer.receiveFrame();
        boolean last = false;
        while(!last) {
            String errorMessage = validateFrame(frame);
            if(errorMessage != null) {
				throw new ProtocolException(
					"Invalid " + errorMessage + "please retry."
				);
			}
            if(frame.charAt(1) == 'D') {
                message = message + splitMessage(frame);
                frame = physicalLayer.receiveFrame();
            } else {
                message = message + splitMessage(frame);
                last = true;
            }    
        }
        return message;
    } // end of method receiveMessage

    /**
    * Checks a frame to ensure that it is valid in structure, message
    * size and that the recieved checkSum is correct 
    * @param frame to be validated
    * @return the errors that frame has
    */
    private String validateFrame(String frame)
    {   
        String errorMessage = "";
        if(!frame.matches("<[E|D]-\\d{2}-.{1,99}-\\d{2}>")) {
            errorMessage = errorMessage + "frame struture, ";
		} else {
            int mtu = physicalLayer.getMTU();
            if(frame.length() > mtu) {
                errorMessage = errorMessage + "frame size exceeds MTU, "; 
			}
            int validLength = splitMessage(frame).length();
            int frameLength = Integer.parseInt(frame.substring(3, 5));
            if(validLength != frameLength) {
                errorMessage = errorMessage + "message size counter, ";
			}
            String validCheckSum = generateCheckSum(frame.substring(0, (frame.length() - 3)));
            String frameCheckSum = frame.substring((frame.length() - 3), (frame.length() - 1));
            if(!validCheckSum.equals(frameCheckSum)) {
                errorMessage = errorMessage + "checkSum current value, ";
			}
		}
        if(errorMessage.length() == 0) {
            return "errorMessage is equal to null";  // null
		} else {
            return errorMessage;
		}
	}

    /**
    * Takes a valid frame and returns the message
    * @param frame to split a message from
    * @return frame message
    */
    private String splitMessage(String frame)
    {
        String message = frame.substring(6, (frame.length() - 4));
        return message;
    }

    /**
     * Takes a frame and calculates the check sum value by the arithmetic 
     * sum of the characters inside the frame. (same as sender)
     * @param frame the frame which is having it's check sum calculated
     * @return the checkSum value of the frame
     */
    private String generateCheckSum(String frame)
    {
        int checkSum = 0;
		//System.out.println("frame: " + frame);
        for(int i = 1; i < frame.length(); i++) {
            checkSum = checkSum + frame.charAt(i); // += 
        }
        String csString = Integer.toString(checkSum);
        String csString2 = csString.substring((csString.length() - 2), csString.length()); 
		// csString2 for debug (checking conversion)
        return csString2;
    }
} // end of class MessageReceiver