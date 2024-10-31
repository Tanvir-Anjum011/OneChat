import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Client implements ActionListener {
    JTextField text;
    static JPanel a1;
    static Box vertical = Box.createVerticalBox();
    static DataOutputStream out;
    static JFrame frame = new JFrame();

    Client (){
        frame.setLayout(null);

        JPanel panel1 = new JPanel();
        panel1.setBackground(new Color(149, 211, 134));
        panel1.setBounds(0,0,450,90);
        frame.add(panel1);
        panel1.setLayout(null);
        frame.setSize(450,700);
        frame.setLocation(200,700);


        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/previous.png"));
        Image i2 = i1.getImage().getScaledInstance(25, 25, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel back = new JLabel(i3);
        back.setBounds(15,30,25,25);
        panel1.add(back);

        back.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                System.exit(0);
            }
        });

        ImageIcon i7 = new ImageIcon(ClassLoader.getSystemResource("icons/Turjo.png"));
        Image i8 = i7.getImage().getScaledInstance(60, 60, Image.SCALE_DEFAULT);
        ImageIcon i9 = new ImageIcon(i8);
        JLabel displayPictureJubayer = new JLabel(i9);
        displayPictureJubayer.setBounds(50,5,80,80);
        panel1.add(displayPictureJubayer);

        ImageIcon i10 = new ImageIcon(ClassLoader.getSystemResource("icons/video.png"));
        Image i11 = i10.getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT);
        ImageIcon i12 = new ImageIcon(i11);
        JLabel video = new JLabel(i12);
        video.setBounds(300,20,50,50);
        panel1.add(video);

        ImageIcon i13 = new ImageIcon(ClassLoader.getSystemResource("icons/call.png"));
        Image i14 = i13.getImage().getScaledInstance(35, 35, Image.SCALE_DEFAULT);
        ImageIcon i15 = new ImageIcon(i14);
        JLabel call = new JLabel(i15);
        call.setBounds(365,25, 35,35);
        panel1.add(call);

        ImageIcon i16 = new ImageIcon(ClassLoader.getSystemResource("icons/more.png"));
        Image i17 = i16.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT);
        ImageIcon i18 = new ImageIcon(i17);
        JLabel more = new JLabel(i18);
        more.setBounds(410,28, 30,30);
        panel1.add(more);

        JLabel name = new JLabel("Turjo");
        name.setBounds(130,30,100,20);
        name.setForeground(Color.BLACK);
        name.setFont(new Font("SAN_SERIF", Font.BOLD, 17));
        panel1.add(name);

        JLabel status = new JLabel("Active Now");
        status.setBounds(140,50,100,20);
        status.setForeground(Color.BLACK);
        status.setFont(new Font("SAN_SERIF", Font.PLAIN, 12));
        panel1.add(status);

        a1 = new JPanel();
        a1.setBounds(0,90,450,500);
        a1.setBackground(new Color(0xCFECB2));
        frame.add(a1);

        ImageIcon i19 = new ImageIcon(ClassLoader.getSystemResource("icons/dot.png"));
        Image i20 = i19.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT);
        ImageIcon i21 = new ImageIcon(i20);
        JLabel activeIcon = new JLabel(i21);
        activeIcon.setBounds(117,46, 30,30);
        panel1.add(activeIcon);

        text = new JTextField();
        text.setBackground(new Color(149, 211, 134));
        text.setBounds(10,600,310,50);
        text.setFont(new Font("SAN_SERIF", Font.PLAIN, 16));
        frame.add(text);

        JButton send = new JButton("Send");
        send.setBackground(new Color(149, 211, 134));
        send.setForeground(Color.BLACK);
        send.addActionListener(this);
        send.setBounds(330,600,100,50);
        send.setFont(new Font("SAN_SERIF", Font.PLAIN, 16));
        frame.add(send);

        frame.getContentPane().setBackground(new Color(231,251,230));
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    public void actionPerformed(ActionEvent e) {
        try {
            String messages = text.getText();

            JPanel panel1 = formatLabel1(messages);

            a1.setLayout(new BorderLayout());

            JPanel right = new JPanel(new BorderLayout());
            right.add(panel1, BorderLayout.LINE_END);
            vertical.add(right);
            vertical.add(Box.createVerticalStrut(15));

            a1.add(vertical, BorderLayout.PAGE_START);
            out.writeUTF(messages);

            text.setText("");

            frame.repaint();
            frame.invalidate();
            frame.validate();
        }
        catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static JPanel formatLabel1 (String out){
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel output = new JLabel("<html> <p style=\"width:150px\">"+ out + "</p></html>");
        output.setFont(new Font("Tahoma",Font.PLAIN,16));
        output.setBackground(new Color(179, 222, 143));
        output.setOpaque(true);
        output.setBorder(new EmptyBorder(10,15,15,20));
        panel.add(output);

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");

        JLabel time = new JLabel(simpleDateFormat.format(calendar.getTime()));
        panel.add(time);

        return panel;
    }

    public static void main(String[] args) {
        new Client();

        try {
            Socket socket = new Socket("127.0.0.1", 8080);
            DataInput in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());

            while (true){
                a1.setLayout(new BorderLayout());
                String message = in.readUTF();
                JPanel panel = formatLabel1(message);

                JPanel left = new JPanel(new BorderLayout());
                left.add(panel, BorderLayout.LINE_START);

                vertical.add(left);
                vertical.add(Box.createVerticalStrut(15));
                a1.add(vertical, BorderLayout.PAGE_START);

                frame.validate();
            }
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
