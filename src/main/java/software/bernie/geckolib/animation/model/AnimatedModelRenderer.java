/*
 * Copyright (c) 2020.
 * Author: Bernie G. (Gecko)
 */

package software.bernie.geckolib.animation.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;

public class AnimatedModelRenderer extends ModelRenderer
{
	private BoneSnapshot boneSnapshot;
	public String currentAnimationName;

	public float scaleValueX = 1;
	public float scaleValueY = 1;
	public float scaleValueZ = 1;

	public float positionOffsetX = 0;
	public float positionOffsetY = 0;
	public float positionOffsetZ = 0;

	public String name;
	public BoneSnapshot initialSnapshot;

	public AnimatedModelRenderer(ModelBase model)
	{
		super(model);
	}


	public void setModelRendererName(String modelRendererName)
	{
		this.name = modelRendererName;
	}

	public AnimatedModelRenderer setTextureOffset(int x, int y)
	{
		this.textureOffsetX = x;
		this.textureOffsetY = y;
		return this;
	}

	public void addBox(float x, float y, float z, float dx, float dy, float dz, float inflate, boolean mirror){
		ModelBox box = new ModelBox(this, this.textureOffsetX, this.textureOffsetY, x, y, z, Math.round(dx), Math.round(dy), Math.round(dz), inflate, mirror);
		this.cubeList.add(box);

	}

	@Override
	public void render(float scale)
	{
		if (!this.isHidden)
		{
			if (this.showModel)
			{
				if (!this.compiled)
				{
					this.compileDisplayList(scale);
				}

				GlStateManager.translate(this.offsetX, this.offsetY, this.offsetZ);

				if (this.rotateAngleX == 0.0F && this.rotateAngleY == 0.0F && this.rotateAngleZ == 0.0F)
				{
					if (this.rotationPointX == 0.0F && this.rotationPointY == 0.0F && this.rotationPointZ == 0.0F)
					{
						GlStateManager.callList(this.displayList);

						if (this.childModels != null)
						{
							for (int k = 0; k < this.childModels.size(); ++k)
							{
								((ModelRenderer) this.childModels.get(k)).render(scale);
							}
						}
					}
					else
					{
						GlStateManager.translate(this.rotationPointX * scale, this.rotationPointY * scale,
								this.rotationPointZ * scale);
						GlStateManager.callList(this.displayList);

						if (this.childModels != null)
						{
							for (int j = 0; j < this.childModels.size(); ++j)
							{
								((ModelRenderer) this.childModels.get(j)).render(scale);
							}
						}

						GlStateManager.translate(-this.rotationPointX * scale, -this.rotationPointY * scale,
								-this.rotationPointZ * scale);
					}
				}
				else
				{
					GlStateManager.pushMatrix();
					GlStateManager.translate(((rotationPointX + positionOffsetX) / 16F), ((rotationPointY - positionOffsetY) / 16F),
							((rotationPointZ + positionOffsetZ) / 16F));

					if (this.rotateAngleZ != 0.0F)
					{
						GlStateManager.rotate(this.rotateAngleZ * (180F / (float) Math.PI), 0.0F, 0.0F, 1.0F);
					}

					if (this.rotateAngleY != 0.0F)
					{
						GlStateManager.rotate(this.rotateAngleY * (180F / (float) Math.PI), 0.0F, 1.0F, 0.0F);
					}

					if (this.rotateAngleX != 0.0F)
					{
						GlStateManager.rotate(this.rotateAngleX * (180F / (float) Math.PI), 1.0F, 0.0F, 0.0F);
					}
					GlStateManager.scale(scaleValueX, scaleValueY, scaleValueZ);

					GlStateManager.callList(this.displayList);

					if (this.childModels != null)
					{
						for (int i = 0; i < this.childModels.size(); ++i)
						{
							((ModelRenderer) this.childModels.get(i)).render(scale);
						}
					}

					GlStateManager.popMatrix();
				}

				GlStateManager.translate(-this.offsetX, -this.offsetY, -this.offsetZ);
			}
		}
	}

	public void saveInitialSnapshot()
	{
		this.initialSnapshot = new BoneSnapshot(this);
	}

	public BoneSnapshot getInitialSnapshot()
	{
		return this.initialSnapshot;
	}

	public BoneSnapshot saveSnapshot()
	{
		return new BoneSnapshot(this);
	}

}