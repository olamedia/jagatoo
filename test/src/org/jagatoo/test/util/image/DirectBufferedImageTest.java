package org.jagatoo.test.util.image;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class DirectBufferedImageTest
{
    public DirectBufferedImageTest() throws Exception
    {
        BufferedImage src = ImageIO.read( new File( "/media/sda6/workspace/xith-tk/test-resources/textures/crate.png" ) );
        
        //BufferedImage dst = new BufferedImage( src.getWidth(), src.getHeight(), BufferedImage.TYPE_4BYTE_ABGR );
        BufferedImage dst = org.jagatoo.image.DirectBufferedImage.makeDirectImageRGBA( src.getWidth(), src.getHeight() );
        
        Graphics2D graphics = dst.createGraphics();
        
        long t0 = System.currentTimeMillis();
        
        for ( int i = 0; i < 500; i++ )
        {
            graphics.drawImage( src, 0, 0, null );
        }
        
        long t1 = System.currentTimeMillis();
        
        System.out.println( ( t1 - t0 ) );
        
        
        /*
        byte[] bs = new byte[ 196608 ];
        ByteBuffer bb = BufferUtils.createByteBuffer( 196608 );
        
        Random rnd = new Random( System.nanoTime() );
        
        long t0 = System.currentTimeMillis();
        
        for ( int i = 0; i < 500; i++ )
        {
            int p = 0;
            for ( int x = 0; x < 256; x++ )
            {
                for ( int y = 0; y < 256; y++ )
                {
                    //bs[ p++ ] = (byte)( rnd.nextInt() & 0xFF );
                    //bb.put( p++, (byte)( rnd.nextInt() & 0xFF ) );
                    bb.put( p++, (byte)12 );
                }
            }
            
            bb.position( 0 );
            bb.limit( 196608 );
            bb.put( bs, 0, bs.length );
        }
        
        long t1 = System.currentTimeMillis();
        
        System.out.println( ( t1 - t0 ) );
        */
    }
    
    public static void main( String[] args ) throws Exception
    {
        new DirectBufferedImageTest();
    }
}
