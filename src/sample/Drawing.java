///*
//هاي بتعملها اول ما تنشئ البانيل
//*/
//img = new BufferedImage(750, 700, BufferedImage.TYPE_INT_BGR );
//		canvas = new JPanel() {
//            /**
//			 *
//			 */
//			private static final long serialVersionUID = 1L;
//
//			protected void paintComponent(Graphics g) {
//                super.paintComponent(g);
//                g.setColor(Color.GRAY);
//                g.drawImage(img, 0, 0, null);
//            }
//        };
//
///*
//هاي اللي بترسم
//هلأ انا عملت مؤشرين ,
//المؤشراكس بزيد 16 بكسل كل ما ارسم حرف , لان حجم الحرف 16*16
//المؤشر التاني بزيد بس لما المؤشر اكس يوصل للطرف
//
//int[][] tmp ==> this is the letter I want to draw
//*/
//
//
//private void drawLetter(WritableRaster r , int[][] tmp ,int[] color ){
//
//    /*
//    عشان انا مخزن الاحرف بالبرنامج ع شكل مصفوفات ثنائيه , برسم بهاي الطريقه
//    */
//			for(int i = 0 ; i < 16 ; i++){
//				for(int j = 0 ; j < 16 ; j++){
//					if(tmp[ i][  j] == 1){
//						r.setPixel(Xpos + j , Ypos  + i, color);
//					}
//				}
//			}
//
//        // هون انا بحكيله ان يمشي المؤشر اكس 16 , طبعا انا بنقص 16 لأن من اليمين للشمال
//			Xpos -= 16 ;
//			System.out.println(Xpos);
//        // وهان بحكيله يفحص اذا وصل اخر السطر
//        // ولازم اخلي المؤشر اكس يرجع للبدايه
//			if(Xpos < 20){
//				Xpos = 670;
//				Ypos += 17 ;
//			}
//    /*
//    جدا جدا جدا جدا مهمات
//    canvas ==> JPanel (NOT the BufferedImage)
//    */
//    canvas.revalidate();
//    canvas.repaint();
//}
//
//
///*
//    and now everytime you want to draw a letter u just call the drawLetter method =3
//*/
//
//_____________________                              _____________________
//`-._:  .:'   `:::  .:\           |\__/|           /::  .:'   `:::  .:.-'
//    \      :          \          |:   |          /         :       /
//     \     ::    .     `-_______/ ::   \_______-'   .      ::   . /
//      |  :   :: ::'  :   :: ::'  :   :: ::'      :: ::'  :   :: :|
//      |     ;::         ;::         ;::         ;::         ;::  |
//      |  .:'   `:::  .:'   `:::  .:'   `:::  .:'   `:::  .:'   `:|
//      /     :           :           :           :           :    \
//     /______::_____     ::    .     ::    .     ::   _____._::____\
//                   `----._:: ::'  :   :: ::'  _.----'
//                          `--.       ;::  .--'
//                              `-. .:'  .-'
//                                 \    /
//                                  \  /
//                                   \/
