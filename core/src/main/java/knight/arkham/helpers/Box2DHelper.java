package knight.arkham.helpers;

import com.badlogic.gdx.physics.box2d.*;
import knight.arkham.objects.Alien;
import knight.arkham.objects.Bullet;
import knight.arkham.objects.Player;

import static knight.arkham.helpers.Constants.*;

public class Box2DHelper {

    public static Body createBody(Box2DBody box2DBody) {

        PolygonShape shape = new PolygonShape();

        shape.setAsBox(box2DBody.bounds.width / 2 / PIXELS_PER_METER, box2DBody.bounds.height / 2 / PIXELS_PER_METER);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;

        fixtureDef.density = box2DBody.density;

        if (box2DBody.userData instanceof Player)
            fixtureDef.filter.categoryBits = PLAYER_BIT;

        else if (box2DBody.userData instanceof Alien)
            fixtureDef.filter.categoryBits = ALIEN_BIT;

        else
            fixtureDef.filter.categoryBits = STRUCTURE_BIT;

        Body body = createBox2DBodyByType(box2DBody);

        body.createFixture(fixtureDef).setUserData(box2DBody.userData);

        shape.dispose();

        return body;
    }

    public static Body createBulletBody(Box2DBody box2DBody) {

        FixtureDef fixtureDef = new FixtureDef();

        CircleShape circleShape = new CircleShape();

        circleShape.setRadius(8 / PIXELS_PER_METER);

        fixtureDef.shape = circleShape;

        fixtureDef.density = box2DBody.density;

        if (box2DBody.userData instanceof Bullet)
            fixtureDef.filter.categoryBits = BULLET_BIT;

        else
            fixtureDef.filter.categoryBits = ALIEN_BULLET_BIT;

        Body body = createBox2DBodyByType(box2DBody);

        body.createFixture(fixtureDef).setUserData(box2DBody.userData);

        circleShape.dispose();

        return body;
    }


    private static Body createBox2DBodyByType(Box2DBody box2DBody) {

        BodyDef bodyDef = new BodyDef();

        bodyDef.type = box2DBody.bodyType;

        bodyDef.position.set(box2DBody.bounds.x / PIXELS_PER_METER, box2DBody.bounds.y / PIXELS_PER_METER);

        bodyDef.fixedRotation = true;

        return box2DBody.world.createBody(bodyDef);
    }
}
